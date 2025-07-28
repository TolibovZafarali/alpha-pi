package com.example.backend.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class InvestorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String contactName;
    private String contactPhone;
    private String state;
    private String investmentRange;

    @Column(columnDefinition = "TEXT")
    private String interests;

    // List of saved businesses
    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL)
    private List<InvestorSavedBusiness> savedBusinesses;

    // Constructors
    public InvestorProfile() {
    }

    public InvestorProfile(Long id, String email, String password, String contactName, String contactPhone, String state, String investmentRange, String interests, List<InvestorSavedBusiness> savedBusinesses) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
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
