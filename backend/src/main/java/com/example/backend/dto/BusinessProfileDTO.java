package com.example.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BusinessProfileDTO {
    private Long id;
    private Long userId;
    private String businessName;
    private String industry;
    private String logoUrl;
    private String description;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private boolean isPublished;
    private BigDecimal fundingGoal;
    private BigDecimal currentRevenue;
    private LocalDate foundedDate;
    private boolean isRunning;
    private List<InterestedInvestorDTO> interestedInvestors;

    // Default constructor
    public BusinessProfileDTO() {
    }

    public BusinessProfileDTO(Long id, Long userId, String businessName, String industry, String logoUrl, String description,
                              String contactName, String contactEmail, String contactPhone, boolean isPublished,
                              BigDecimal fundingGoal, BigDecimal currentRevenue, LocalDate foundedDate, boolean isRunning, List<InterestedInvestorDTO> interestedInvestors) {
        this.id = id;
        this.userId = userId;
        this.businessName = businessName;
        this.industry = industry;
        this.logoUrl = logoUrl;
        this.description = description;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.isPublished = isPublished;
        this.fundingGoal = fundingGoal;
        this.currentRevenue = currentRevenue;
        this.foundedDate = foundedDate;
        this.isRunning = isRunning;
        this.interestedInvestors = interestedInvestors;
    }

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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public BigDecimal getFundingGoal() {
        return fundingGoal;
    }

    public void setFundingGoal(BigDecimal fundingGoal) {
        this.fundingGoal = fundingGoal;
    }

    public BigDecimal getCurrentRevenue() {
        return currentRevenue;
    }

    public void setCurrentRevenue(BigDecimal currentRevenue) {
        this.currentRevenue = currentRevenue;
    }

    public LocalDate getFoundedDate() {
        return foundedDate;
    }

    public void setFoundedDate(LocalDate foundedDate) {
        this.foundedDate = foundedDate;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public List<InterestedInvestorDTO> getInterestedInvestors() {
        return interestedInvestors;
    }

    public void setInterestedInvestors(List<InterestedInvestorDTO> interestedInvestors) {
        this.interestedInvestors = interestedInvestors;
    }
}
