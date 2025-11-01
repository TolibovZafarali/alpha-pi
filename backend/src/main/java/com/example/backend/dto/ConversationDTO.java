package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
public class ConversationDTO {
    private Long id;
    private OtherUser otherUser;
    private LastMessage lastMessage;
    private Long businessId;
    private Long investorId;
    private int unreadCount;

    @Getter
    @Setter
    public static class OtherUser {
        private Long id;
        private String name;
        private String photoUrl;
    }
    @Getter
    @Setter
    public static class LastMessage {
        private Long senderId;
        private String content;
        private Instant createdAt;
    }
}
