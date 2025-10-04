package com.example.backend.service;

import com.example.backend.dto.ConversationDTO;
import com.example.backend.dto.MessageDTO;
import com.example.backend.model.Conversation;
import com.example.backend.model.Message;
import com.example.backend.repository.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
public class ChatService {

    private final ConversationRepository conversationRepo;
    private final MessageRepository messageRepo;
    private final BusinessProfileRepository businessRepo;
    private final InvestorProfileRepository investorRepo;
    private final UserRepository userRepo;

    public ChatService(ConversationRepository conversationRepo,
                       MessageRepository messageRepo,
                       BusinessProfileRepository businessRepo,
                       InvestorProfileRepository investorRepo,
                       UserRepository userRepo) {
        this.conversationRepo = conversationRepo;
        this.messageRepo = messageRepo;
        this.businessRepo = businessRepo;
        this.investorRepo = investorRepo;
        this.userRepo = userRepo;
    }

    public boolean isParticipant(Long conversationId, Long userId) {
        return conversationRepo.findById(conversationId)
                .map(c -> c.getUser1Id().equals(userId) || c.getUser2Id().equals(userId))
                .orElse(false);
    }

    @Transactional
    public Conversation startOrGetConversation(Long callerUserId, Long businessId, Long investorId) {
        Long otherUserId = resolveOtherUserId(callerUserId, businessId, investorId);

        long low = Math.min(callerUserId, otherUserId);
        long high = Math.max(callerUserId, otherUserId);

        Optional<Conversation> existing = conversationRepo.findByUser1IdAndUser2Id(low, high);
        if (existing.isPresent()) return existing.get();

        Conversation c = new Conversation();
        c.setUser1Id(low);
        c.setUser2Id(high);
        c.setBusinessId(businessId);
        c.setInvestorId(investorId);
        return conversationRepo.save(c);
    }

    @Transactional(readOnly = true)
    public Page<ConversationDTO> listConversations(Long callerUserId, Pageable pageable) {
        Page<Conversation> page = conversationRepo.findByUser1IdOrUser2IdOrderByUpdatedAtDesc(callerUserId, callerUserId, pageable);
        return page.map(c -> toConversationDTO(c, callerUserId));
    }

    @Transactional(readOnly = true)
    public ConversationDTO getConversation(Long conversationId, Long callerUserId) {
        Conversation conversation = conversationRepo.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        if (!conversation.hasParticipant(callerUserId)) throw new ResponseStatusException(FORBIDDEN);
        return toConversationDTO(conversation, callerUserId);
    }

    @Transactional(readOnly = true)
    public Page<MessageDTO> getMessages(Long conversationId, Long callerUserId, Pageable pageable) {
        ensureParticipant(conversationId, callerUserId);
        return messageRepo.findByConversationIdOrderByCreatedAtDesc(conversationId, pageable)
                .map(this::toMessageDTO);
    }

    @Transactional
    public MessageDTO sendMessage(Long conversationId, Long senderId, String content) {
        if (content == null || content.isBlank() || content.length() > 4000) {
            throw new ResponseStatusException(BAD_REQUEST, "Invalid message length");
        }
        Conversation c = conversationRepo.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        if (!isParticipant(conversationId, senderId)) throw new ResponseStatusException(FORBIDDEN);

        Message m = new Message();
        m.setConversationId(conversationId);
        m.setSenderId(senderId);
        m.setContent(content.strip());
        Message saved = messageRepo.save(m);

        c.setUpdatedAt(Instant.now());
        conversationRepo.save(c);

        return toMessageDTO(saved);
    }

    @Transactional
    public void markRead(Long conversationId, Long readerId) {
        ensureParticipant(conversationId, readerId);
        messageRepo.markUnreadAsRead(conversationId, readerId, Instant.now());
    }

    @Transactional(readOnly = true)
    public long unreadCount(Long userId) {
        return messageRepo.countAllUnreadForUser(userId);
    }

    /* helpers */

    private void ensureParticipant(Long conversationId, Long userId) {
        if (!isParticipant(conversationId, userId)) throw new ResponseStatusException(FORBIDDEN);
    }

    private Long resolveOtherUserId(Long callerUserId, Long businessId, Long investorId) {
        if (businessId != null && investorId == null) {
            var business = businessRepo.findById(businessId)
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Business not found"));
            Long ownerUserId = business.getUser().getId();
            if (ownerUserId.equals(callerUserId)) throw new ResponseStatusException(BAD_REQUEST, "Cannot chat with yourself");
            return ownerUserId;
        } else if (investorId != null && businessId == null) {
            var investor = investorRepo.findById(investorId)
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Investor not found"));
            Long invUserId = investor.getUser().getId();
            if (invUserId.equals(callerUserId)) throw new ResponseStatusException(BAD_REQUEST, "Cannot chat with yourself");
            return invUserId;
        } else {
            throw new ResponseStatusException(BAD_REQUEST, "Provide exactly one of businessId or investorId");
        }
    }

    private ConversationDTO toConversationDTO(Conversation c, Long callerUserId) {
        Long otherId = c.getUser1Id().equals(callerUserId) ? c.getUser2Id() : c.getUser1Id();

        var user = userRepo.findById(otherId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User missing"));
        String displayName = user.getEmail(); // if you add fullName later, prefer that
        String photoUrl = null; // your User currently doesn’t have avatar; safe to keep null

        // last message (cheap page of size 1)
        var lastPage = messageRepo.findByConversationIdOrderByCreatedAtDesc(c.getId(), PageRequest.of(0, 1));
        ConversationDTO.LastMessage lm = null;
        if (!lastPage.isEmpty()) {
            var last = lastPage.getContent().get(0);
            lm = new ConversationDTO.LastMessage();
            lm.setSenderId(last.getSenderId());
            lm.setContent(last.getContent());
            lm.setCreatedAt(last.getCreatedAt());
        }

        int unread = (int) messageRepo.countUnreadInConversation(c.getId(), callerUserId);

        var other = new ConversationDTO.OtherUser();
        other.setId(otherId);
        other.setName(displayName);
        other.setPhotoUrl(photoUrl);

        var dto = new ConversationDTO();
        dto.setId(c.getId());
        dto.setOtherUser(other);
        dto.setLastMessage(lm);
        dto.setBusinessId(c.getBusinessId());
        dto.setInvestorId(c.getInvestorId());
        dto.setUnreadCount(unread);
        return dto;
    }

    private MessageDTO toMessageDTO(Message m) {
        var dto = new MessageDTO();
        dto.setId(m.getId());
        dto.setSenderId(m.getSenderId());
        dto.setContent(m.getContent());
        dto.setCreatedAt(m.getCreatedAt());
        dto.setReadAt(m.getReadAt());
        return dto;
    }
}
