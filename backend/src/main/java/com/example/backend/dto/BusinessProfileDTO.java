package com.example.backend.dto;

import java.time.LocalDate;
import java.util.List;

public class BusinessProfileDTO {
    private Long id;
    private String email;
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
    private Boolean isRunning;

    private List<InterestedInvestorDTO> interestedInvestors;
}
