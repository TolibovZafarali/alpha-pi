package com.example.backend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "business_profile")
public class BusinessProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key for the business profile

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user; // User associated with this business profile


    @Column(nullable = false)
    private String businessName; // Name of the business

    @Column(nullable = false)
    private String industry; // Industry of the business

    @Column(nullable = false)
    private String logoUrl; // URL of the business logo

    @Column(nullable = false)
    private String description; // Description of the business


    @Column(nullable = false)
    private String contactName; // Contact person's name

    @Column(nullable = false)
    private String contactEmail; // Contact person's email

    @Column(nullable = false)
    private String contactPhone; // Contact person's phone number


    @Column(nullable = false)
    private boolean isPublished; // Indicates if the business profile is published


    @OneToMany(mappedBy = "businessProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvestorSavedBusiness> savedByInvestors; // List of investors who have saved this business profile

    // Constructors

    public BusinessProfile() {
    }

    public BusinessProfile(User user, String businessName, String industry, String logoUrl, String description,
                           String contactName, String contactEmail, String contactPhone, boolean isPublished) {
        this.user = user;
        this.businessName = businessName;
        this.industry = industry;
        this.logoUrl = logoUrl;
        this.description = description;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.isPublished = isPublished;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<InvestorSavedBusiness> getSavedByInvestors() {
        return savedByInvestors;
    }

    public void setSavedByInvestors(List<InvestorSavedBusiness> savedByInvestors) {
        this.savedByInvestors = savedByInvestors;
    }
}
