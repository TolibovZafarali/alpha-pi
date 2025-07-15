package com.example.backend.model;


import jakarta.persistence.*;

@Entity
@Table(name = "potential_location")
public class PotentialLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key for the location

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_profile_id", nullable = false)
    private BusinessProfile businessProfile; // Business profile associated with this location

    @Column(nullable = false)
    private String streetAddress; // Street address of the potential location

    @Column(nullable = false)
    private String city; // City of the potential location

    @Column(nullable = false)
    private String state; // State of the potential location

    @Column(nullable = false, length = 5)
    private String zipCode; // Zip code of the potential location

    private String companyName; // Name of the company at this location

    private String companyPhone; // Phone number of the company at this location

    private String companyEmail; // Email of the company at this location

    private String costInfo; // Cost information for the potential location

    private String dealBreakers; // Deal breakers for the potential location

    private String spaceNotes; // Additional notes about the potential location

    // Constructors

    public PotentialLocation() {
    }

    public PotentialLocation(BusinessProfile businessProfile, String streetAddress, String city, String state, String zipCode, String companyName, String companyPhone, String companyEmail, String costInfo, String dealBreakers, String spaceNotes) {
        this.businessProfile = businessProfile;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.companyName = companyName;
        this.companyPhone = companyPhone;
        this.companyEmail = companyEmail;
        this.costInfo = costInfo;
        this.dealBreakers = dealBreakers;
        this.spaceNotes = spaceNotes;
    }

    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessProfile getBusinessProfile() {
        return businessProfile;
    }

    public void setBusinessProfile(BusinessProfile businessProfile) {
        this.businessProfile = businessProfile;
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
