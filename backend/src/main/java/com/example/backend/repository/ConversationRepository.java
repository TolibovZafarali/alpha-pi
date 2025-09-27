package com.example.backend.repository;

import com.example.backend.model.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findByUser1IdAndUser2Id(Long user1Id, Long user2Id);
    Page<Conversation> findByUser1IdOrUser2IdOrderByUpdatedAtDesc(Long u1, Long u2, Pageable pageable);
}
