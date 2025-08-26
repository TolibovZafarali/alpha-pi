package com.example.backend.dto;

import java.time.LocalDate;
import java.util.List;

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

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public Double getFundingGoal() {
        return fundingGoal;
    }

    public void setFundingGoal(Double fundingGoal) {
        this.fundingGoal = fundingGoal;
    }

    public Double getCurrentRevenue() {
        return currentRevenue;
    }

    public void setCurrentRevenue(Double currentRevenue) {
        this.currentRevenue = currentRevenue;
    }

    public LocalDate getFoundedDate() {
        return foundedDate;
    }

    public void setFoundedDate(LocalDate foundedDate) {
        this.foundedDate = foundedDate;
    }

    public List<InterestedInvestorDTO> getInterestedInvestors() {
        return interestedInvestors;
    }

    public void setInterestedInvestors(List<InterestedInvestorDTO> interestedInvestors) {
        this.interestedInvestors = interestedInvestors;
    }
}
