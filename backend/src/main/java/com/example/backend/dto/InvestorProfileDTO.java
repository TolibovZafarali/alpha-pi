package com.example.backend.dto;

import java.util.List;

public class InvestorProfileDTO {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String photoUrl;
    private String businessEmail;
    private String businessPhone;
    private Integer minInvestment;
    private Integer maxInvestment;
    private String state;
    private String interests;
    private List<InvestorSavedBusinessDTO> savedBusinesses;

    // Default constructor
    public InvestorProfileDTO() {
    }

    public InvestorProfileDTO(Long id, Long userId, String firstName, String lastName, String photoUrl,
                              String businessEmail, String businessPhone, Integer minInvestment,
                              Integer maxInvestment, String state, String interests,
                              List<InvestorSavedBusinessDTO> savedBusinesses) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoUrl = photoUrl;
        this.businessEmail = businessEmail;
        this.businessPhone = businessPhone;
        this.minInvestment = minInvestment;
        this.maxInvestment = maxInvestment;
        this.state = state;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public Integer getMinInvestment() {
        return minInvestment;
    }

    public void setMinInvestment(Integer minInvestment) {
        this.minInvestment = minInvestment;
    }

    public Integer getMaxInvestment() {
        return maxInvestment;
    }

    public void setMaxInvestment(Integer maxInvestment) {
        this.maxInvestment = maxInvestment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
