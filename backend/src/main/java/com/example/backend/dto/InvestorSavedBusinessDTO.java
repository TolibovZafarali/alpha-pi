package com.example.backend.dto;

public class InvestorSavedBusinessDTO {
    private Long id;
    private Long investorProfileId;
    private Long businessProfileId;
    private String businessName;
    private String industry;
    private boolean isPublished;
    private String savedAt;

    // === Getters and Setters ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvestorProfileId() {
        return investorProfileId;
    }

    public void setInvestorProfileId(Long investorProfileId) {
        this.investorProfileId = investorProfileId;
    }

    public Long getBusinessProfileId() {
        return businessProfileId;
    }

    public void setBusinessProfileId(Long businessProfileId) {
        this.businessProfileId = businessProfileId;
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

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public String getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(String savedAt) {
        this.savedAt = savedAt;
    }
}
