package com.example.backend.controller;

import com.example.backend.dto.BusinessProfileDTO;
import com.example.backend.model.BusinessProfile;
import com.example.backend.repository.BusinessProfileRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/businesses")
public class BusinessProfileController {

    private final BusinessProfileRepository businessRepo;

    public BusinessProfileController(BusinessProfileRepository businessRepo) {
        this.businessRepo = businessRepo;
    }

    // Browse: return all PUBLISHED businesses
    @GetMapping
    public ResponseEntity<List<BusinessProfileDTO>> getAllPublished() {
        List<BusinessProfile> published = businessRepo.findAllByIsPublishedTrue();
        return ResponseEntity.ok(
                published.stream().map(this::toDto).toList()
        );
    }

    // Fetch a business profile by its profile id
    @GetMapping("/profile/{profileId}")
    public ResponseEntity<BusinessProfileDTO> getByProfileId(@PathVariable Long profileId) {
        Optional<BusinessProfile> bp = businessRepo.findById(profileId);
        return bp.map(businessProfile -> ResponseEntity.ok(toDto(businessProfile)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // === Mapping helpers ===

    private BusinessProfileDTO toDto(BusinessProfile e) {
        BusinessProfileDTO d = new BusinessProfileDTO();
        d.setId(e.getId());
        d.setUserId(e.getUser() != null ? e.getUser().getId() : null);

        d.setBusinessName(e.getBusinessName());
        d.setIndustry(e.getIndustry());
        d.setDescription(e.getDescription());
        d.setLogoUrl(e.getLogoUrl());
        d.setFundingGoal(e.getFundingGoal());
        d.setCurrentRevenue(e.getCurrentRevenue());
        d.setFoundedDate(e.getFoundedDate());
        d.setContactName(e.getContactName());
        d.setContactEmail(e.getContactEmail());
        d.setContactPhone(e.getContactPhone());
        d.setPublished(e.isPublished());

        return d;
    }
}