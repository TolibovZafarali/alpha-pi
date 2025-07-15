package com.example.backend.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.List;

@Entity
@Table(name = "investor_profile")
public class InvestorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user; // User associated with this investor profile

    @Column(nullable = false)
    private String firstName; // Investor's first name

    @Column(nullable = false)
    private String lastName; // Investor's last name

    @Column(nullable = false)
    private String photoUrl; // URL of the investor's profile picture

    @Email
    @Column(nullable = false)
    private String businessEmail; // Investor's business email

    @Column(nullable = false)
    private String businessPhone; // Investor's business phone number

    @OneToMany(mappedBy = "investorProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvestorSavedBusiness> savedBusinesses; // List of businesses saved by the investor

    // Constructors

    public InvestorProfile() {
    }

    public InvestorProfile(User user, String firstName, String lastName, String photoUrl, String businessEmail, String businessPhone) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoUrl = photoUrl;
        this.businessEmail = businessEmail;
        this.businessPhone = businessPhone;
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
}
