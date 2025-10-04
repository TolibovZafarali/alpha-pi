package com.example.backend.controller;

import com.example.backend.dto.*;
import com.example.backend.security.JwtAuthenticationFilter.JwtUserAuthentication;
import com.example.backend.service.ChatService;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chat;

    public ChatController(ChatService chat) {
        this.chat = chat;
    }

    private Long me(Authentication auth) {
        if (auth instanceof JwtUserAuthentication j) return j.getUserId();
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/start")
    public ConversationDTO start(@RequestBody StartChatRequest req, Authentication auth) {
        Long uid = me(auth);
        var c = chat.startOrGetConversation(uid, req.getBusinessId(), req.getInvestorId());
        return chat.getConversation(c.getId(), uid);
    }

    @GetMapping("/conversations")
    public Page<ConversationDTO> list(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "20") int size,
                                      Authentication auth) {
        Long uid = me(auth);
        return chat.listConversations(uid, PageRequest.of(page, size));
    }

    @GetMapping("/conversations/{id}/messages")
    public Page<MessageDTO> messages(@PathVariable Long id,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "30") int size,
                                     Authentication auth) {
        Long uid = me(auth);
        return chat.getMessages(id, uid, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
    }

    @PostMapping("/conversations/{id}/messages")
    public MessageDTO send(@PathVariable Long id,
                           @RequestBody SendMessageRequest body,
                           Authentication auth) {
        Long uid = me(auth);
        return chat.sendMessage(id, uid, body.getContent());
    }

    @PostMapping("/conversations/{id}/read")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markRead(@PathVariable Long id, Authentication auth) {
        Long uid = me(auth);
        chat.markRead(id, uid);
    }

    @GetMapping("/unread-count")
    public long unread(Authentication auth) {
        Long uid = me(auth);
        return chat.unreadCount(uid);
    }
}
