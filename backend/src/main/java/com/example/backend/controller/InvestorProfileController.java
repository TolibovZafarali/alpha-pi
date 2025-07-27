package com.example.backend.controller;

import com.example.backend.dto.InvestorProfileDTO;
import com.example.backend.dto.InvestorSavedBusinessDTO;
import com.example.backend.model.BusinessProfile;
import com.example.backend.model.InvestorProfile;
import com.example.backend.model.User;
import com.example.backend.repository.InvestorProfileRepository;
import com.example.backend.repository.InvestorSavedBusinessRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/investors")
public class InvestorProfileController {

    @Autowired
    private InvestorProfileRepository investorProfileRepository;

    @Autowired
    private InvestorSavedBusinessRepository savedBusinessRepository;

    @Autowired
    private UserRepository userRepository;

    // CREATE
    @PostMapping("/{userId}")
    public ResponseEntity<?> createInvestorProfile(@PathVariable Long userId, @RequestBody InvestorProfileDTO profileDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        if (investorProfileRepository.findByUserId(userId).isPresent()) {
            return ResponseEntity.badRequest().body("This user already has an investor profile.");
        }

        InvestorProfile newProfile = new InvestorProfile(
                optionalUser.get(), profileDTO.getFirstName(), profileDTO.getLastName(),
                profileDTO.getPhotoUrl(), profileDTO.getBusinessEmail(), profileDTO.getBusinessPhone(),
                profileDTO.getMinInvestment(), profileDTO.getMaxInvestment(), profileDTO.getState(),
                profileDTO.getInterests()
        );

        investorProfileRepository.save(newProfile);
        return ResponseEntity.ok("Investor profile created successfully.");
    }

    // UPDATE
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateInvestorProfile(@PathVariable Long userId, @RequestBody InvestorProfileDTO profileDTO) {
        Optional<InvestorProfile> optionalProfile = investorProfileRepository.findByUserId(userId);

        if (optionalProfile.isEmpty()) {
            return ResponseEntity.status(404).body("Investor profile not found");
        }

        InvestorProfile profile = optionalProfile.get();

        if (!profile.getUser().getId().equals(userId)) {
            return ResponseEntity.status(403).body("You are not authorized to update this profile");
        }

        profile.setFirstName(profileDTO.getFirstName());
        profile.setLastName(profileDTO.getLastName());
        profile.setPhotoUrl(profileDTO.getPhotoUrl());
        profile.setBusinessEmail(profileDTO.getBusinessEmail());
        profile.setBusinessPhone(profileDTO.getBusinessPhone());
        profile.setMinInvestment(profileDTO.getMinInvestment());
        profile.setMaxInvestment(profileDTO.getMaxInvestment());
        profile.setState(profileDTO.getState());
        profile.setInterests(profileDTO.getInterests());

        investorProfileRepository.save(profile);
        return ResponseEntity.ok("Investor profile updated successfully.");
    }

    // GET OWN PROFILE
    @GetMapping("/{userId}")
    public ResponseEntity<?> getInvestorProfile(@PathVariable Long userId) {
        Optional<InvestorProfile> optionalProfile = investorProfileRepository.findByUserId(userId);

        if (optionalProfile.isEmpty()) {
            return ResponseEntity.status(404).body("Investor profile not found");
        }

        InvestorProfile profile = optionalProfile.get();

        // Convert saved businesses into DTOs
        List<InvestorSavedBusinessDTO> savedBusinessDTOs = profile.getSavedBusinesses().stream()
                .map(saved -> {
                    BusinessProfile business = saved.getBusinessProfile();

                    return new InvestorSavedBusinessDTO(
                            saved.getId(),
                            business.getId(),
                            business.getBusinessName(),
                            business.getIndustry(),
                            saved.getSavedAt()
                    );
                }).collect(Collectors.toList());

        InvestorProfileDTO dto = new InvestorProfileDTO(
                profile.getId(),
                profile.getUser().getId(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getPhotoUrl(),
                profile.getBusinessEmail(),
                profile.getBusinessPhone(),
                profile.getMinInvestment(),
                profile.getMaxInvestment(),
                profile.getState(),
                profile.getInterests(),
                savedBusinessDTOs
        );

        return ResponseEntity.ok(dto);
    }
}
