package com.example.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "investor_saved_businesses")
public class InvestorSavedBusiness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key for the saved business record

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investor_profile_id", nullable = false)
    private InvestorProfile investorProfile; // Investor who saved the business

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_profile_id", nullable = false)
    private BusinessProfile businessProfile; // Business profile that was saved

    @Column(nullable = false)
    private LocalDateTime savedAt; // Timestamp when the business was saved

    // Constructors

    public InvestorSavedBusiness() {
    }

    public InvestorSavedBusiness(InvestorProfile investorProfile, BusinessProfile businessProfile, LocalDateTime savedAt) {
        this.investorProfile = investorProfile;
        this.businessProfile = businessProfile;
        this.savedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InvestorProfile getInvestorProfile() {
        return investorProfile;
    }

    public void setInvestorProfile(InvestorProfile investorProfile) {
        this.investorProfile = investorProfile;
    }

    public BusinessProfile getBusinessProfile() {
        return businessProfile;
    }

    public void setBusinessProfile(BusinessProfile businessProfile) {
        this.businessProfile = businessProfile;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }
}
