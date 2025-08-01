package com.example.backend.dto;

import java.util.List;

public class InvestorProfileDTO {
    private Long id;
    private String email;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String photoUrl;
    private String state;
    private String investmentRange;
    private String interests;

    private List<InvestorSavedBusinessDTO> savedBusinesses;

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

    public List<InvestorSavedBusinessDTO> getSavedBusinesses() {
        return savedBusinesses;
    }

    public void setSavedBusinesses(List<InvestorSavedBusinessDTO> savedBusinesses) {
        this.savedBusinesses = savedBusinesses;
    }
}
