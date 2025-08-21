package com.example.backend.controller;

import com.example.backend.dto.AuthDtos;
import com.example.backend.model.BusinessProfile;
import com.example.backend.model.InvestorProfile;
import com.example.backend.model.User;
import com.example.backend.repository.BusinessProfileRepository;
import com.example.backend.repository.InvestorProfileRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.JwtUtils;
import com.example.backend.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final RefreshTokenService refreshTokens;

    public AuthController(UserRepository users,
                          BusinessProfileRepository businessProfiles,
                          InvestorProfileRepository investorProfiles,
                          BCryptPasswordEncoder encoder,
                          AuthenticationManager authManager,
                          JwtUtils jwt,
                          RefreshTokenService refreshTokens) {
        this.users = users;
        this.businessProfiles = businessProfiles;
        this.investorProfiles = investorProfiles;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwt = jwt;
        this.refreshTokens = refreshTokens;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody AuthDtos.SignupRequest req) {
        if (users.existsByEmail(req.email)) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        var u = new User();
        u.setEmail(req.email);
        u.setPasswordHash(encoder.encode(req.password));
        u.setRole(User.Role.valueOf(req.role == null ? "BUSINESS" : req.role.toUpperCase()));
        u.setTokenVersion(0);
        var saved = users.save(u);

        switch (saved.getRole()) {
            case BUSINESS -> { var bp = new BusinessProfile(); bp.setUser(saved); businessProfiles.save(bp); }
            case INVESTOR -> { var ip = new InvestorProfile(); ip.setUser(saved); investorProfiles.save(ip); }
        }

        // issue tokens
        String access = jwt.issueAccessToken(saved.getId(), saved.getEmail(), saved.getRole().name(), saved.getTokenVersion());
        String refresh = refreshTokens.issue(saved);
        return ResponseEntity.ok(new AuthResponseTokens(saved.getId(), saved.getEmail(), saved.getRole().name(), "Signed up", access, refresh));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDtos.LoginRequest req) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.email, req.password));
        var principal = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        var user = users.findByEmail(principal.getUsername()).orElseThrow();

        String access = jwt.issueAccessToken(user.getId(), user.getEmail(), user.getRole().name(), user.getTokenVersion());
        String refresh = refreshTokens.issue(user);
        return ResponseEntity.ok(new AuthResponseTokens(user.getId(), user.getEmail(), user.getRole().name(), "Logged in", access, refresh));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@Valid @RequestBody AuthDtos.RefreshRequest req) {

        var anyUser = refreshTokens.validateAndRotate(req.refreshToken, /*expectedUserId*/ 0L);
        if (anyUser.isEmpty()) return ResponseEntity.status(401).body("invalid refresh token");

        var user = anyUser.get();
        String access = jwt.issueAccessToken(user.getId(), user.getEmail(), user.getRole().name(), user.getTokenVersion());
        String newRefresh = refreshTokens.issue(user); // rotation
        return ResponseEntity.ok(new Tokens(access, newRefresh));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody AuthDtos.RefreshRequest req) {
        refreshTokens.revokeRaw(req.refreshToken);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changePassword(@Valid @RequestBody AuthDtos.ChangePasswordRequest req,
                                            org.springframework.security.core.Authentication auth) {

        Object details = auth.getDetails();
        if (!(details instanceof Long uid)) return ResponseEntity.status(401).build();

        var user = users.findById(uid).orElseThrow();
        if (!encoder.matches(req.currentPassword, user.getPasswordHash())) {
            return ResponseEntity.status(400).body("current password incorrect");
        }
        user.setPasswordHash(encoder.encode(req.newPassword));
        user.setTokenVersion(user.getTokenVersion() + 1); // invalidate old access tokens
        users.save(user);

        refreshTokens.revokeAll(user); // revoke all refresh tokens
        return ResponseEntity.ok().build();
    }

    // response types
    public static class Tokens {
        public String accessToken;
        public String refreshToken;
        public Tokens(String a, String r) { this.accessToken = a; this.refreshToken = r; }
    }

    public static class AuthResponseTokens extends AuthDtos.AuthResponse {
        public String accessToken;
        public String refreshToken;
        public AuthResponseTokens(Long userId, String email, String role, String message, String access, String refresh) {
            super(userId, email, role, message);
            this.accessToken = access; this.refreshToken = refresh;
        }
    }
}
