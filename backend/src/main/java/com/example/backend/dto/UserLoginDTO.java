package com.example.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for User Login.
 * This class is used to transfer user login data between the client and server.
 */
public class UserLoginDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    // Getters and Setters

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
}
