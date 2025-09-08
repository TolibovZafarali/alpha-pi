package com.example.backend.service;

import com.example.backend.model.RefreshToken;
import com.example.backend.model.User;
import com.example.backend.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repo;
    private final SecureRandom rng = new SecureRandom();
    private final int ttlDays;

    public RefreshTokenService(RefreshTokenRepository repo, @Value("${app.jwt.refresh-ttl-days:14}") int ttlDays) {
        this.repo = repo;
        this.ttlDays = ttlDays;
    }

    // Create a new opaque refresh token for user and persist its hash
    public String issue(User user) {
        String raw = randomToken();
        String hash = hash(raw);
        RefreshToken rt = new RefreshToken();
        rt.setUser(user);
        rt.setTokenHash(hash);
        rt.setCreatedAt(LocalDateTime.now());
        rt.setExpiresAt(LocalDateTime.now().plusDays(ttlDays));
        repo.save(rt);
        return raw; // return the raw to the client once
    }

    @Transactional
    public Optional<RotationResult> validateAndRotate (String raw) {
        String hash = hash(raw);
        var opt = repo.findByTokenHash(hash);
        if (opt.isEmpty()) return Optional.empty();
        var token = opt.get();
        if (!token.isActive()) return Optional.empty();

        // revoke old token
        token.setRevokedAt(LocalDateTime.now());
        repo.save(token);

        // issue a new token for the same user
        String newToken = issue(token.getUser());
        return Optional.of(new RotationResult(token.getUser(), newToken));
    }

    public record RotationResult(User user, String newToken) {}

    public void revokeRaw(String raw) {
        var opt = repo.findByTokenHash(hash(raw));
        opt.ifPresent(rt -> {
            rt.setRevokedAt(LocalDateTime.now());
            repo.save(rt);
        });
    }

    public void revokeAll(User user) {
        repo.findAllByUser(user).forEach(rt -> {
            if (rt.getRevokedAt() == null) {
                rt.setRevokedAt(LocalDateTime.now());
                repo.save(rt);
            }
        });
    }

    private String randomToken() {
        byte[] buf = new byte[32]; // 256-bit
        rng.nextBytes(buf);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
    }

    private String hash(String raw) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(raw.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        } catch (Exception e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
