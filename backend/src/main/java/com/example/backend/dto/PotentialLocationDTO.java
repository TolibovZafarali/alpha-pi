package com.example.backend.dto;

public class PotentialLocationDTO {

    private Long id;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String companyName;
    private String companyPhone;
    private String companyEmail;
    private String costInfo;
    private String dealBreakers;
    private String spaceNotes;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCostInfo() {
        return costInfo;
    }

    public void setCostInfo(String costInfo) {
        this.costInfo = costInfo;
    }

    public String getDealBreakers() {
        return dealBreakers;
    }

    public void setDealBreakers(String dealBreakers) {
        this.dealBreakers = dealBreakers;
    }

    public String getSpaceNotes() {
        return spaceNotes;
    }

    public void setSpaceNotes(String spaceNotes) {
        this.spaceNotes = spaceNotes;
    }
}
