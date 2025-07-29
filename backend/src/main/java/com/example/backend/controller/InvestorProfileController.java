package com.example.backend.controller;

import com.example.backend.dto.InvestorProfileDTO;
import com.example.backend.dto.InvestorSavedBusinessDTO;
import com.example.backend.model.BusinessProfile;
import com.example.backend.model.InvestorProfile;
import com.example.backend.model.InvestorSavedBusiness;
import com.example.backend.repository.BusinessProfileRepository;
import com.example.backend.repository.InvestorProfileRepository;
import com.example.backend.repository.InvestorSavedBusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/investors")
public class InvestorProfileController {

    @Autowired
    private InvestorProfileRepository investorProfileRepository;

    @Autowired
    private InvestorSavedBusinessRepository savedBusinessRepository;

    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // SIGN UP
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody InvestorProfile investor) {
        if (investorProfileRepository.findByEmail(investor.getEmail()).isPresent() || businessProfileRepository.findByEmail(investor.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        investor.setPassword(passwordEncoder.encode(investor.getPassword()));
        InvestorProfile saved = investorProfileRepository.save(investor);
        return ResponseEntity.ok(saved.getId());
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody InvestorProfile loginReq) {
        Optional<InvestorProfile> optional = investorProfileRepository.findByEmail(loginReq.getEmail());
        if (optional.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        InvestorProfile stored = optional.get();
        if (!passwordEncoder.matches(loginReq.getPassword(), stored.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        return ResponseEntity.ok(stored.getId());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody InvestorProfile updatedData) {
        Optional<InvestorProfile> optional = investorProfileRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        InvestorProfile existing = optional.get();
        existing.setContactName(updatedData.getContactName());
        existing.setContactPhone(updatedData.getContactPhone());
        existing.setState(updatedData.getState());
        existing.setInvestmentRange(updatedData.getInvestmentRange());
        existing.setInterests(updatedData.getInterests());

        investorProfileRepository.save(existing);
        return ResponseEntity.ok("Profile updated");
    }

    // GET Investor + Saved Businesses
    public ResponseEntity<?> getInvestorWithSavedBusinesses(@PathVariable Long id) {
        Optional<InvestorProfile> optional = investorProfileRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(404).body("Investor profile not found");
        }

        InvestorProfile investor = optional.get();

        List<InvestorSavedBusiness> savedList = savedBusinessRepository.findByInvestor(investor);
        List<InvestorSavedBusinessDTO> savedDTOs = new ArrayList<>();

        for (InvestorSavedBusiness saved : savedList) {
            BusinessProfile business = saved.getBusiness();
            InvestorSavedBusinessDTO dto = new InvestorSavedBusinessDTO();
            dto.setId(saved.getId());
            dto.setBusinessId(business.getId());
            dto.setBusinessName(business.getBusinessName());
            dto.setIndustry(business.getIndustry());
            dto.setLogoUrl(business.getLogoUrl());
            dto.setSavedAt(saved.getSavedAt());
            savedDTOs.add(dto);
        }

        // Build DTO
        InvestorProfileDTO profileDTO = new InvestorProfileDTO();
        profileDTO.setId(investor.getId());
        profileDTO.setEmail(investor.getEmail());
        profileDTO.setContactName(investor.getContactName());
        profileDTO.setContactPhone(investor.getContactPhone());
        profileDTO.setState(investor.getState());
        profileDTO.setInvestmentRange(investor.getInvestmentRange());
        profileDTO.setInterests(investor.getInterests());
        profileDTO.setSavedBusinesses(savedDTOs);

        return ResponseEntity.ok(profileDTO);
    }
}
