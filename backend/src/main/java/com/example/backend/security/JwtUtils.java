package com.example.backend.security;

import io.jsonwebtoken.*;
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
    private final String issuer;
    private final String audience;

    public JwtUtils(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.access-ttl-min}") long accessTtlMin,
            @Value("${app.jwt.issuer:alpha-pi}") String issuer,
            @Value("${app.jwt.audience:alpha-pi-web}") String audience) {
        this.secretBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.accessTtlMin = accessTtlMin;
        this.issuer = issuer;
        this.audience = audience;
    }

    public String issueAccessToken(Long userId, String email, String role) {
        Instant now = Instant.now();
        return Jwts.builder()
                .issuer(issuer)
                .subject(email)
                .audience().add(audience).and()
                .claim("uid", userId)
                .claim("role", role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(accessTtlMin, ChronoUnit.MINUTES)))
                .signWith(Keys.hmacShaKeyFor(secretBytes), Jwts.SIG.HS256)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        var parser = Jwts.parser()
                .clockSkewSeconds(60) // allow +/- 60s clock drift
                .requireIssuer(issuer)
                .requireAudience(audience)
                .verifyWith(Keys.hmacShaKeyFor(secretBytes))
                .build();
        return parser.parseSignedClaims(token);
    }
}
