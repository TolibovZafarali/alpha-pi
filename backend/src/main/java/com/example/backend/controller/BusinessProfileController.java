package com.example.backend.controller;

import com.example.backend.dto.BusinessProfileDTO;
import com.example.backend.model.BusinessProfile;
import com.example.backend.model.User;
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
    private final UserRepository userRepo;

    public BusinessProfileController(BusinessProfileRepository businessRepo, UserRepository userRepo) {
        this.businessRepo = businessRepo;
        this.userRepo = userRepo;
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

    // Fetch the business profile attached to a given user id.
    @GetMapping("{usedId}")
    public ResponseEntity<BusinessProfileDTO> getByUserId(@PathVariable Long userId) {
        Optional<BusinessProfile> bp = businessRepo.findByUserId(userId);
        return bp.map(businessProfile -> ResponseEntity.ok(toDto(businessProfile)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Create (or upsert) a business profile for a user.
    // If a profile already exists for the user, we update it; else we create a new one.
    @PostMapping("{userId}")
    public ResponseEntity<BusinessProfileDTO> createOrUpsert(@PathVariable Long userId, @RequestBody BusinessProfileDTO payload) {
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        BusinessProfile entity = businessRepo.findByUserId(userId).orElseGet(BusinessProfile::new);
        entity.setUser(user);
        fromDto(payload, entity);

        BusinessProfile saved = businessRepo.save(entity);
        return ResponseEntity.status(HttpStatus.OK).body(toDto(saved));
    }

    // Update only the fields of the business profile for a given user id
    @PutMapping("{userId}")
    public ResponseEntity<BusinessProfileDTO> update(@PathVariable Long userId, @RequestBody BusinessProfileDTO payload) {
        Optional<BusinessProfile> existingOpt = businessRepo.findByUserId(userId);
        if (existingOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        BusinessProfile existing = existingOpt.get();
        fromDto(payload, existing);
        BusinessProfile saved = businessRepo.save(existing);
        return ResponseEntity.ok(toDto(saved));
    }

    // Toggle publish on/off
    @PatchMapping("{userId}/publish")
    public ResponseEntity<BusinessProfileDTO> setPublish(@PathVariable Long userId, @RequestParam("value") boolean value) {
        Optional<BusinessProfile> existingOpt = businessRepo.findByUserId(userId);
        if (existingOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        BusinessProfile existing = existingOpt.get();
        existing.setPublished(value);
        BusinessProfile saved = businessRepo.save(existing);
        return ResponseEntity.ok(toDto(saved));
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

    private void fromDto(BusinessProfileDTO d, BusinessProfile e) {
        if (d.getBusinessName() != null) e.setBusinessName(d.getBusinessName());
        if (d.getIndustry() != null) e.setIndustry(d.getIndustry());
        if (d.getDescription() != null) e.setDescription(d.getDescription());
        if (d.getLogoUrl() != null) e.setLogoUrl(d.getLogoUrl());
        if (d.getFundingGoal() != null) e.setFundingGoal(d.getFundingGoal());
        if (d.getCurrentRevenue() != null) e.setCurrentRevenue(d.getCurrentRevenue());
        if (d.getFoundedDate() != null) e.setFoundedDate(d.getFoundedDate());
        if (d.getContactName() != null) e.setContactName(d.getContactName());
        if (d.getContactEmail() != null) e.setContactEmail(d.getContactEmail());
        if (d.getContactPhone() != null) e.setContactPhone(d.getContactPhone());
        if (d.getPublished() != null) e.setPublished(d.getPublished());
    }
}