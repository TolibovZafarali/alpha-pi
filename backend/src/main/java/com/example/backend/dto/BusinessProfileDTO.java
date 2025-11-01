package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessProfileDTO {
    private Long id;
    private Long userId;
    private String businessName;
    private String industry;
    private String description;
    private String logoUrl;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private Boolean isPublished;
    private Double fundingGoal;
    private Double currentRevenue;
    private LocalDate foundedDate;

    private List<InterestedInvestorDTO> interestedInvestors;
}
