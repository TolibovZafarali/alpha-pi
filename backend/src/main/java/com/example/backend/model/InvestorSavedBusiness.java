package com.example.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class InvestorSavedBusiness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "investor_id")
    private InvestorProfile investor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "business_id")
    private BusinessProfile business;

    @Column(nullable = false)
    private LocalDateTime savedAt = LocalDateTime.now();

    // Constructors
    public InvestorSavedBusiness() {
    }

    public InvestorSavedBusiness(Long id, InvestorProfile investor, BusinessProfile business, LocalDateTime savedAt) {
        this.id = id;
        this.investor = investor;
        this.business = business;
        this.savedAt = savedAt;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InvestorProfile getInvestor() {
        return investor;
    }

    public void setInvestor(InvestorProfile investor) {
        this.investor = investor;
    }

    public BusinessProfile getBusiness() {
        return business;
    }

    public void setBusiness(BusinessProfile business) {
        this.business = business;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }
}
