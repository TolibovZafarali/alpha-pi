package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvestorSavedBusinessDTO {
    // Getters and Setters
    private Long id;
    private Long businessId;
    private String businessName;
    private String industry;
    private String description;
    private String logoUrl;

    private String contactName;
    private String contactEmail;
    private String contactPhone;

    private Double fundingGoal;
    private Double currentRevenue;
    private LocalDate foundedDate;
    private LocalDateTime savedAt;

}
