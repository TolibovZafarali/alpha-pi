package com.example.backend.dto;

import java.time.LocalDateTime;

public class InvestorSavedBusinessDTO {
    private Long id;
    private Long businessId;
    private String businessName;
    private String industry;
    private LocalDateTime savedAt;

    // Default constructor
    public InvestorSavedBusinessDTO() {
    }

    public InvestorSavedBusinessDTO(Long id, Long businessId, String businessName, String industry, LocalDateTime savedAt) {
        this.id = id;
        this.businessId = businessId;
        this.businessName = businessName;
        this.industry = industry;
        this.savedAt = savedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }
}
