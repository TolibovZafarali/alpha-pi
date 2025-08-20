package com.example.backend.controller;

import com.example.backend.dto.AuthDtos;
import com.example.backend.model.BusinessProfile;
import com.example.backend.model.InvestorProfile;
import com.example.backend.model.User;
import com.example.backend.repository.BusinessProfileRepository;
import com.example.backend.repository.InvestorProfileRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.JwtUtils;
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
    private final JwtUtils jwt;

    public AuthController(UserRepository users, BusinessProfileRepository businessProfiles, InvestorProfileRepository investorProfiles, BCryptPasswordEncoder encoder, AuthenticationManager authManager, JwtUtils jwt) {
        this.users = users;
        this.businessProfiles = businessProfiles;
        this.investorProfiles = investorProfiles;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwt = jwt;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AuthDtos.SignupRequest req) {
        if (users.existsByEmail(req.email)) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        var u = new User();
        u.setEmail(req.email);
        u.setPasswordHash(encoder.encode(req.password));
        u.setRole(User.Role.valueOf(req.role == null ? "BUSINESS" : req.role.toUpperCase()));
        var saved = users.save(u);

        switch (saved.getRole()) {
            case BUSINESS -> { var bp = new BusinessProfile(); bp.setUser(saved); businessProfiles.save(bp); }
            case INVESTOR -> { var ip = new InvestorProfile(); ip.setUser(saved); investorProfiles.save(ip); }
        }

        var accessToken = jwt.issueAccessToken(saved.getId(), saved.getEmail(), saved.getRole().name());
        return ResponseEntity.ok(new AuthResponseWithToken(saved.getId(), saved.getEmail(), saved.getRole().name(), "Signed up", accessToken));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDtos.LoginRequest req) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.email, req.password));
        var principal = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        var user = users.findByEmail(principal.getUsername()).orElseThrow();

        var accessToken = jwt.issueAccessToken(user.getId(), user.getEmail(), user.getRole().name());
        return ResponseEntity.ok(new AuthResponseWithToken(user.getId(), user.getEmail(), user.getRole().name(), "Logged in", accessToken));
    }

    public static class AuthResponseWithToken extends AuthDtos.AuthResponse {
        public String accessToken;
        public AuthResponseWithToken(Long userId, String email, String role, String message, String accessToken) {
            super(userId, email, role, message);
            this.accessToken = accessToken;
        }
    }
}
