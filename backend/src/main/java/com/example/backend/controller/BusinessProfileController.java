package com.example.backend.controller;

import com.example.backend.dto.BusinessProfileDTO;
import com.example.backend.model.BusinessProfile;
import com.example.backend.model.User;
import com.example.backend.repository.BusinessProfileRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/businesses")
public class BusinessProfileController {

    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    @Autowired
    private UserRepository userRepository;

    // CREATE
    @PostMapping("/{userId}")
    public ResponseEntity<?> createBusinessProfile(@PathVariable Long userId, @RequestBody BusinessProfileDTO profileDTO) {

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        if (businessProfileRepository.findByUserId(userId).isPresent()) {
            return ResponseEntity.badRequest().body("This user already has a business profile.");
        }

        BusinessProfile newProfile = new BusinessProfile(
                optionalUser.get(), profileDTO.getBusinessName(), profileDTO.getIndustry(), profileDTO.getLogoUrl(), profileDTO.getDescription(),
                profileDTO.getContactName(), profileDTO.getContactEmail(), profileDTO.getContactPhone(), profileDTO.isPublished(),
                profileDTO.getFundingGoal(), profileDTO.getCurrentRevenue(), profileDTO.getFoundedDate(), profileDTO.isRunning()
        );

        businessProfileRepository.save(newProfile);
        return ResponseEntity.ok("Business profile created successfully.");
    }

    // UPDATE
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateBusinessProfile(@PathVariable Long userId, @RequestBody BusinessProfileDTO profileDTO) {
        Optional<BusinessProfile> optionalProfile = businessProfileRepository.findByUserId(userId);

        if (optionalProfile.isEmpty()) {
            return ResponseEntity.status(404).body("Business profile not found");
        }

        BusinessProfile profile = optionalProfile.get();

        if (!profile.getUser().getId().equals(userId)) {
            return ResponseEntity.status(403).body("You are not authorized to update this profile");
        }

        profile.setBusinessName(profileDTO.getBusinessName());
        profile.setIndustry(profileDTO.getIndustry());
        profile.setLogoUrl(profileDTO.getLogoUrl());
        profile.setDescription(profileDTO.getDescription());
        profile.setContactName(profileDTO.getContactName());
        profile.setContactEmail(profileDTO.getContactEmail());
        profile.setContactPhone(profileDTO.getContactPhone());
        profile.setPublished(profileDTO.isPublished());
        profile.setFundingGoal(profileDTO.getFundingGoal());
        profile.setCurrentRevenue(profileDTO.getCurrentRevenue());
        profile.setFoundedDate(profileDTO.getFoundedDate());
        profile.setRunning(profileDTO.isRunning());

        businessProfileRepository.save(profile);
        return ResponseEntity.ok("Business profile updated successfully");
    }

    // GET ONE BUSINESS PROFILE
    @GetMapping("/{id}")
    public ResponseEntity<?> getBusinessById(@PathVariable Long id) {
        Optional<BusinessProfile> profile = businessProfileRepository.findById(id);

        if (profile.isPresent()) {
            return ResponseEntity.ok(profile.get());
        } else {
            return ResponseEntity.status(404).body("Business profile not found");
        }
    }

    // GET ALL BUSINESS PROFILES
    @GetMapping
    public ResponseEntity<List<BusinessProfile>> getAllBusinesses() {
        List<BusinessProfile> businesses = businessProfileRepository.findAll();
        return ResponseEntity.ok(businesses);
    }
}