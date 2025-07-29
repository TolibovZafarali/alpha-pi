package com.example.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class BusinessProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String businessName;
    private String industry;
    private String description;
    private String logoUrl;

    private String contactName;
    private String contactEmail;
    private String contactPhone;

    private Boolean isPublished = false;
    private Double fundingGoal;
    private Double currentRevenue;
    private LocalDate foundedDate;
    private Boolean isRunning;

    // Investors who saved this business
    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private List<InvestorSavedBusiness> interestedInvestors;

    // Constructors
    public BusinessProfile() {
    }

    public BusinessProfile(Long id, String email, String password, String businessName, String industry, String description, String logoUrl, String contactName, String contactEmail, String contactPhone, Boolean isPublished, Double fundingGoal, Double currentRevenue, LocalDate foundedDate, Boolean isRunning, List<InvestorSavedBusiness> interestedInvestors) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.businessName = businessName;
        this.industry = industry;
        this.description = description;
        this.logoUrl = logoUrl;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean isPublished() {
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

    public Boolean isRunning() {
        return isRunning;
    }

    public void setRunning(Boolean running) {
        isRunning = running;
    }

    public List<InvestorSavedBusiness> getInterestedInvestors() {
        return interestedInvestors;
    }

    public void setInterestedInvestors(List<InvestorSavedBusiness> interestedInvestors) {
        this.interestedInvestors = interestedInvestors;
    }
}
