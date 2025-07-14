package com.example.backend.model;


import jakarta.persistence.*;

@Entity
@Table(name="user") // Table name in the database
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //Primary key

    private String email; // User's email

    private String password; // User's password

    private String role; // User's role (e.g., "Business Owner" or "Investor")


}
