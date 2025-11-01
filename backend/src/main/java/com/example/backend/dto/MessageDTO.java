package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class MessageDTO {
    private Long id;
    private Long senderId;
    private String content;
    private Instant createdAt;
    private Instant readAt;
}
