package com.example.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwt;

    public JwtAuthenticationFilter(JwtUtils jwt) {
        this.jwt = jwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Jws<Claims> jws = jwt.parse(token);
                Claims c = jws.getPayload();
                Long userId = c.get("uid", Number.class).longValue();
                String email = c.getSubject();
                String role = c.get("role", String.class);

                var auth = new JwtUserAuthentication(userId, email, List.of(new SimpleGrantedAuthority("ROLE_" + role)));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ignored) {}
        }
        chain.doFilter(req, res);
    }

    static class JwtUserAuthentication extends AbstractAuthenticationToken {
        private final Long userId;
        private final String email;

        JwtUserAuthentication(Long userId, String email, List<SimpleGrantedAuthority> auths) {
            super(auths);
            this.userId = userId;
            this.email = email;
            setAuthenticated(true);
        }
        @Override public Object getCredentials() { return ""; }
        @Override public Object getPrincipal() { return email; }
        public Long getUserId() { return userId; }
        public String getEmail() { return email; }
    }
}
