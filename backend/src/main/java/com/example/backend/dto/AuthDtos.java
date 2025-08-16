package com.example.backend.dto;

public class AuthDtos {
    public static class SignupRequest {
        public String email;
        public String password;
        public String role;
        public String contactName;
    }

    public static class LoginRequest {
        public String email;
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
}
