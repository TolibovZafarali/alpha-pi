package com.example.backend.controller;

import com.example.backend.dto.InterestedInvestorDTO;
import com.example.backend.model.InvestorSavedBusiness;
import com.example.backend.repository.BusinessProfileRepository;
import com.example.backend.repository.InvestorProfileRepository;
import com.example.backend.repository.InvestorSavedBusinessRepository;
import com.example.backend.security.JwtAuthenticationFilter.JwtUserAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class InvestorSavedBusinessController {

    private final InvestorSavedBusinessRepository savedRepo;
    private final InvestorProfileRepository investorRepo;
    private final BusinessProfileRepository businessRepo;

    public InvestorSavedBusinessController(InvestorSavedBusinessRepository savedRepo, InvestorProfileRepository investorRepo, BusinessProfileRepository businessRepo) {
        this.savedRepo = savedRepo;
        this.investorRepo = investorRepo;
        this.businessRepo = businessRepo;
    }

    @PostMapping("/api/me/saved/{businessId}")
    @PreAuthorize("hasRole('INVESTOR')")
    public ResponseEntity<?> saveMyBusiness(@PathVariable Long businessId, Authentication auth) {
        var me = (JwtUserAuthentication) auth;
        var investor = investorRepo.findByUserId(me.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Investor profile not found"));
        var business = businessRepo.findById(businessId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Business profile not found"));

        var existing = savedRepo.findByInvestorAndBusiness(investor, business);
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body("Already saved");
        }
        var saved = new InvestorSavedBusiness();
        saved.setInvestor(investor);
        saved.setBusiness(business);
        saved.setSavedAt(LocalDateTime.now());
        savedRepo.save(saved);
        return ResponseEntity.ok("Business saved");
    }

    @DeleteMapping("/api/me/saved/{businessId}")
    @PreAuthorize("hasRole('INVESTOR')")
    public ResponseEntity<?> unsaveMyBusiness(@PathVariable Long businessId, Authentication auth) {
        var me = (JwtUserAuthentication) auth;
        var investor = investorRepo.findByUserId(me.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Investor profile not found"));
        var business = businessRepo.findById(businessId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Business profile not found"));
        var existing = savedRepo.findByInvestorAndBusiness(investor, business)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Save record not found"));
        savedRepo.delete(existing);
        return ResponseEntity.ok("Business unsaved");
    }

    // === For business owners: see investors who saved "my" business ===
    @GetMapping("/api/me/business/interested")
    @PreAuthorize("hasRole('BUSINESS')")
    public ResponseEntity<List<InterestedInvestorDTO>> getInterestedInMyBusiness(Authentication auth) {
        var me = (JwtUserAuthentication) auth;
        var myBusiness = businessRepo.findByUserId(me.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Business profile not found"));
        var savedList = savedRepo.findByBusiness(myBusiness);
        var out = savedList.stream().map(sb -> {
            var inv = sb.getInvestor();
            var dto = new InterestedInvestorDTO();
            dto.setContactName(inv.getContactName());
            dto.setContactEmail(inv.getContactEmail());
            dto.setContactPhone(inv.getContactPhone());
            dto.setPhotoUrl(inv.getPhotoUrl());
            dto.setState(inv.getState());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(out);
    }
}
