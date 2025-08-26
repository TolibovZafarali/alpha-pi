
package com.example.backend.config;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;

@Component
public class StartupSanity {
    private static final Logger log = LoggerFactory.getLogger(StartupSanity.class);

    public StartupSanity(
            @Value("${app.jwt.access-ttl-min}") long ttl,
            @Value("${app.jwt.secret}") String secret) {
        int bytes = secret.getBytes(StandardCharsets.UTF_8).length;
        log.info("JWT configured: ttl={} min, secretBytes={}", ttl, bytes);
    }
}
