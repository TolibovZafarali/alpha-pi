package com.example.backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class InvestorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_investor_profile_user")
    )
    private User user;

    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String photoUrl;
    private String state;
    private String investmentRange;

    @Column(columnDefinition = "TEXT")
    private String interests;

    // List of saved businesses
    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<InvestorSavedBusiness> savedBusinesses;

    // Constructors
    public InvestorProfile() {
    }

    public InvestorProfile(Long id, String contactName, String contactEmail, String contactPhone, String photoUrl, String state, String investmentRange, String interests, List<InvestorSavedBusiness> savedBusinesses) {
        this.id = id;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.photoUrl = photoUrl;
        this.state = state;
        this.investmentRange = investmentRange;
        this.interests = interests;
        this.savedBusinesses = savedBusinesses;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInvestmentRange() {
        return investmentRange;
    }

    public void setInvestmentRange(String investmentRange) {
        this.investmentRange = investmentRange;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public List<InvestorSavedBusiness> getSavedBusinesses() {
        return savedBusinesses;
    }

    public void setSavedBusinesses(List<InvestorSavedBusiness> savedBusinesses) {
        this.savedBusinesses = savedBusinesses;
    }
}
