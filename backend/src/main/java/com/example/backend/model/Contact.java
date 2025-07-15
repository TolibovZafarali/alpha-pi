package com.example.backend.model;

import jakarta.persistence.*;

/**
 * Contact entity representing a contact person associated with a user.
 * This entity is used to store contact information for business owners or investors.
 */

@Entity
@Table(name = "contact") // Table name in the database
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key for the contact
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User associated with this contact

    @Column(nullable = false)
    private String name; // Name of the contact person

    @Column(nullable = false)
    private String email; // Email of the contact person

    @Column(nullable = false)
    private String phone; // Phone number of the contact person

    // Constructors

    public Contact() {
    }

    public Contact(User user, String name, String email, String phone) {
        this.user = user;
        this.name = name;
        this.email = email;
        this.phone = phone;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
