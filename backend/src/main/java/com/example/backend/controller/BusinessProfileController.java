package com.example.backend.controller;

import com.example.backend.dto.BusinessProfileDTO;
import com.example.backend.service.BusinessProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/businesses")
public class BusinessProfileController {

    private final BusinessProfileService businessService;

    // Browse: return all PUBLISHED businesses
    @GetMapping
    public ResponseEntity<List<BusinessProfileDTO>> getAllPublished() {
        return ResponseEntity.ok(businessService.getAllPublished());
    }

    // Fetch a business profile by its profile id
    @GetMapping("/profile/{profileId}")
    public ResponseEntity<BusinessProfileDTO> getByProfileId(@PathVariable Long profileId) {
        Optional<BusinessProfileDTO> bp = businessService.getByProfileId(profileId);
        return bp.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}