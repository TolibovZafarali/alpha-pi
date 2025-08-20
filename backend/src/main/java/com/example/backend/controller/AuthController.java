package com.example.backend.controller;

import com.example.backend.dto.AuthDtos;
import com.example.backend.model.BusinessProfile;
import com.example.backend.model.InvestorProfile;
import com.example.backend.model.User;
import com.example.backend.repository.BusinessProfileRepository;
import com.example.backend.repository.InvestorProfileRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository users;
    private final BusinessProfileRepository businessProfiles;
    private final InvestorProfileRepository investorProfiles;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authManager;

    public AuthController(UserRepository users, BusinessProfileRepository businessProfiles, InvestorProfileRepository investorProfiles, BCryptPasswordEncoder encoder, AuthenticationManager authManager) {
        this.users = users;
        this.businessProfiles = businessProfiles;
        this.investorProfiles = investorProfiles;
        this.encoder = encoder;
        this.authManager = authManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AuthDtos.SignupRequest req) {
        if (users.existsByEmail(req.email)) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        var u = new User();
        u.setEmail(req.email);
        u.setPasswordHash(encoder.encode(req.password));
        try {
            u.setRole(User.Role.valueOf(req.role == null ? "BUSINESS" : req.role.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid role");
        }
        var saved = users.save(u);

        // seed empty profile for convenience
        switch (saved.getRole()) {
            case BUSINESS -> {
                var bp = new BusinessProfile();
                bp.setUser(saved);
                bp.setContactName(req.contactName);
                businessProfiles.save(bp);
            }
            case INVESTOR -> {
                var ip = new InvestorProfile();
                ip.setUser(saved);
                ip.setContactName(req.contactName);
                investorProfiles.save(ip);
            }
            default -> {}
        }

        return ResponseEntity.ok(new AuthDtos.AuthResponse(saved.getId(), saved.getEmail(), saved.getRole().name(), "Signed up"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDtos.LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email, req.password)
        );

        var principal = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        var user = users.findByEmail(principal.getUsername()).orElseThrow();
        return ResponseEntity.ok(new AuthDtos.AuthResponse(user.getId(), user.getEmail(), user.getRole().name(), "Logged in"));
    }
}
