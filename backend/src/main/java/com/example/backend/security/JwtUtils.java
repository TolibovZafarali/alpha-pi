package com.example.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtils {

    private final byte[] secretBytes;
    private final long accessTtlMin;

    public JwtUtils(@Value("${app.jwt.secret") String secret,
                    @Value("${app.jwt.access-ttl-min") long accessTtlMin) {
        this.secretBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.accessTtlMin = accessTtlMin;
    }

    public String issueAccessToken(Long userId, String email, String role) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(email)
                .claim("uid", userId)
                .claim("role", role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(accessTtlMin, ChronoUnit.MINUTES)))
                .signWith(Keys.hmacShaKeyFor(secretBytes), Jwts.SIG.HS256)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretBytes))
                .build()
                .parseSignedClaims(token);
    }
}
