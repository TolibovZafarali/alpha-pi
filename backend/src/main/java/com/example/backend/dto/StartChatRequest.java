package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StartChatRequest {
    private Long businessId;
    private Long investorId;
}
