package com.example.backend.repository;

import com.example.backend.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {
    Page<Message> findByConversationIdOrderByCreatedAtDesc(Long conversationId, Pageable pageable);

    @Modifying
    @Query("""
      UPDATE Message m
      SET m.readAt = :now
      WHERE m.conversationId = :conversationId
        AND m.senderId <> :readerId
        AND m.readAt IS NULL
    """)
    int markUnreadAsRead(@Param("conversationId") Long conversationId,
                         @Param("readerId") Long readerId,
                         @Param("now") Instant now);

    @Query("""
      SELECT COUNT(m) FROM Message m
      WHERE m.conversationId = :conversationId
        AND m.senderId <> :userId
        AND m.readAt IS NULL
    """)
    long countUnreadInConversation(@Param("conversationId") Long conversationId,
                                   @Param("userId") Long userId);

    @Query("""
      SELECT COUNT(m) FROM Message m
      WHERE m.senderId <> :userId
        AND m.readAt IS NULL
        AND m.conversationId IN (
          SELECT c.id FROM Conversation c WHERE c.user1Id = :userId OR c.user2Id = :userId
        )
    """)
    long countAllUnreadForUser(@Param("userId") Long userId);
}
