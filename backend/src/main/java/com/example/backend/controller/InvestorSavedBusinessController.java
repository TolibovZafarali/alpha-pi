package com.example.backend.controller;

import com.example.backend.model.BusinessProfile;
import com.example.backend.model.InvestorProfile;
import com.example.backend.model.InvestorSavedBusiness;
import com.example.backend.repository.BusinessProfileRepository;
import com.example.backend.repository.InvestorProfileRepository;
import com.example.backend.repository.InvestorSavedBusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/saved")
public class InvestorSavedBusinessController {

    @Autowired
    private InvestorProfileRepository investorProfileRepository;

    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    @Autowired
    private InvestorSavedBusinessRepository savedBusinessRepository;

    // SAVE a business
    @PostMapping("/{investorId}/{businessId}")
    public ResponseEntity<?> saveBusiness(@PathVariable Long investorId, @PathVariable Long businessId) {

        Optional<InvestorProfile> investorOpt = investorProfileRepository.findByUserId(investorId);
        Optional<BusinessProfile> businessOpt = businessProfileRepository.findById(businessId);

        if (investorOpt.isEmpty() || businessOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Investor or Business not found.");
        }

        InvestorProfile investor = investorOpt.get();
        BusinessProfile business = businessOpt.get();

        // Validation for already being saved
        boolean alreadySaved = savedBusinessRepository.existsByInvestorProfileAndBusinessProfile(investor, business);

        if (alreadySaved) {
            return ResponseEntity.badRequest().body("This business is already saved.");
        }

        InvestorSavedBusiness saved = new InvestorSavedBusiness(investor, business, LocalDateTime.now());
        savedBusinessRepository.save(saved);

        return ResponseEntity.ok("Business saved successfully.");
    }

    // UNSAVE a business
    @DeleteMapping("/{investorId}/{businessId}")
    public ResponseEntity<?> unsaveBusiness(@PathVariable Long investorId, @PathVariable Long businessId) {

        Optional<InvestorProfile> investorOpt = investorProfileRepository.findByUserId(investorId);
        Optional<BusinessProfile> businessOpt = businessProfileRepository.findById(businessId);

        if (investorOpt.isEmpty() || businessOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Investor or Business not found.");
        }

        InvestorProfile investor = investorOpt.get();
        BusinessProfile business = businessOpt.get();

        Optional<InvestorSavedBusiness> savedOpt = savedBusinessRepository.findByInvestorProfileAndBusinessProfile(investor, business);
        if (savedOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("This business is not saved.");
        }

        savedBusinessRepository.delete(savedOpt.get());
        return ResponseEntity.ok("Business unsaved successfully.");
    }
}
