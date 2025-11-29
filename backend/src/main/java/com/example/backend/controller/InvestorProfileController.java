package com.example.backend.controller;

import com.example.backend.dto.InvestorProfileDTO;
import com.example.backend.service.InvestorProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/investors")
public class InvestorProfileController {

    private final InvestorProfileService investorService;

    // Fetch by profile id
    @GetMapping("/profile/{profileId}")
    public ResponseEntity<InvestorProfileDTO> getByProfileId(@PathVariable Long profileId) {
        Optional<InvestorProfileDTO> ip = investorService.getByProfileId(profileId);
        return ip.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
