package com.example.backend.controller;

import com.example.backend.model.BusinessProfile;
import com.example.backend.model.InvestorProfile;
import com.example.backend.model.InvestorSavedBusiness;
import com.example.backend.repository.BusinessProfileRepository;
import com.example.backend.repository.InvestorProfileRepository;
import com.example.backend.repository.InvestorSavedBusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/saved")
public class InvestorSavedBusinessController {

    @Autowired
    private InvestorProfileRepository investorProfileRepository;

    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    @Autowired
    private InvestorSavedBusinessRepository savedBusinessRepository;

    // SAVE a Business
    @PostMapping("/{investorId}/{businessId}")
    public ResponseEntity<?> saveBusiness(@PathVariable Long investorId, @PathVariable Long businessId) {
        Optional<InvestorProfile> investorOpt = investorProfileRepository.findById(investorId);
        Optional<BusinessProfile> businessOpt = businessProfileRepository.findById(businessId);

        if (investorOpt.isEmpty() || businessOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Investor or business not found");
        }

        // Check if a duplicate exists
        Optional<InvestorSavedBusiness> existing = savedBusinessRepository
                .findByInvestorAndBusiness(investorOpt.get(), businessOpt.get());

        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body("Already saved");
        }

        InvestorSavedBusiness saved = new InvestorSavedBusiness();
        saved.setInvestor(investorOpt.get());
        saved.setBusiness(businessOpt.get());
        saved.setSavedAt(java.time.LocalDateTime.now());

        savedBusinessRepository.save(saved);
        return ResponseEntity.ok("Business saved");
    }

    // UNSAVE a Business
    @DeleteMapping("/{investorId}/{businessId}")
    public ResponseEntity<?> unsaveBusiness(@PathVariable Long investorId, @PathVariable Long businessId) {
        Optional<InvestorProfile> investorOpt = investorProfileRepository.findById(investorId);
        Optional<BusinessProfile> businessOpt = businessProfileRepository.findById(businessId);

        if (investorOpt.isEmpty() || businessOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Investor or business not found");
        }

        Optional<InvestorSavedBusiness> existing = savedBusinessRepository
                .findByInvestorAndBusiness(investorOpt.get(), businessOpt.get());

        if (existing.isEmpty()) {
            return ResponseEntity.status(404).body("Save record not found");
        }

        savedBusinessRepository.delete(existing.get());
        return ResponseEntity.ok("Business unsaved");
    }

    // GET all Investors who saved a specific Business
    @GetMapping("/business/{businessId}")
    public ResponseEntity<?> getInterestedInvestors(@PathVariable Long businessId) {
        Optional<BusinessProfile> businessOpt = businessProfileRepository.findById(businessId);
        if (businessOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Business not found");
        }

        List<InvestorSavedBusiness> savedList = savedBusinessRepository.findByBusiness(businessOpt.get());

        List<Map<String, Object>> investors = new ArrayList<>();
        for (InvestorSavedBusiness saved : savedList) {
            InvestorProfile investor = saved.getInvestor();
            Map<String, Object> investorData = new HashMap<>();
            investorData.put("contactName", investor.getContactName());
            investorData.put("contactPhone", investor.getContactPhone());
            investorData.put("state", investor.getState());
            investors.add(investorData);
        }

        return ResponseEntity.ok(investors);
    }
}
