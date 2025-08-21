package com.example.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AuthDtos {
    public static class SignupRequest {
        @Email
        @NotBlank
        public String email;

        @NotBlank
        @Size(min=8)
        public String password;

        @Pattern(regexp = "(?i)^(BUSINESS|INVESTOR)$",
            message = "role must be BUSINESS or INVESTOR")
        public String role;
        public String contactName;
    }

    public static class LoginRequest {
        @Email
        @NotBlank
        public String email;

        @NotBlank
        public String password;
    }

    public static class AuthResponse {
        public Long userId;
        public String email;
        public String role;
        public String message;

        public AuthResponse(Long userId, String email, String role, String message) {
            this.userId = userId;
            this.email = email;
            this.role = role;
            this.message = message;
        }
    }

    public static class RefreshRequest {
        @NotBlank
        public String refreshToken;
    }

    public static class ChangePasswordRequest {
        @NotBlank public String currentPassword;
        @NotBlank @Size(min=8, message="Password must be at least 8 characters")
        public String newPassword;
    }
}
