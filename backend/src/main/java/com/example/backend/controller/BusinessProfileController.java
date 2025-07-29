package com.example.backend.controller;

import com.example.backend.dto.BusinessProfileDTO;
import com.example.backend.dto.InterestedInvestorDTO;
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
@RequestMapping("/api/businesses")
public class BusinessProfileController {

    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    @Autowired
    private InvestorProfileRepository investorProfileRepository;

    @Autowired
    private InvestorSavedBusinessRepository savedBusinessRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // SIGN UP
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody BusinessProfile business) {
        if (businessProfileRepository.findByEmail(business.getEmail()).isPresent() || investorProfileRepository.findByEmail(business.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        business.setPassword(passwordEncoder.encode(business.getPassword()));
        BusinessProfile saved = businessProfileRepository.save(business);
        return ResponseEntity.ok(saved.getId());
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody BusinessProfile loginReq) {
        Optional<BusinessProfile> optional = businessProfileRepository.findByEmail(loginReq.getEmail());
        if (optional.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        BusinessProfile stored = optional.get();
        if (!passwordEncoder.matches(loginReq.getPassword(), stored.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        return ResponseEntity.ok(stored.getId());
    }

    // UPDATE BUSINESS PROFILE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody BusinessProfile updatedData) {
        Optional<BusinessProfile> optional = businessProfileRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BusinessProfile existing = optional.get();
        existing.setBusinessName(updatedData.getBusinessName());
        existing.setIndustry(updatedData.getIndustry());
        existing.setDescription(updatedData.getDescription());
        existing.setLogoUrl(updatedData.getLogoUrl());
        existing.setContactName(updatedData.getContactName());
        existing.setContactEmail(updatedData.getContactEmail());
        existing.setContactPhone(updatedData.getContactPhone());
        existing.setPublished(updatedData.isPublished());
        existing.setFundingGoal(updatedData.getFundingGoal());
        existing.setCurrentRevenue(updatedData.getCurrentRevenue());
        existing.setFoundedDate(updatedData.getFoundedDate());
        existing.setRunning(updatedData.isRunning());

        businessProfileRepository.save(existing);
        return ResponseEntity.ok("Profile updated");
    }

    // GET Single Profile
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<BusinessProfile> optional = businessProfileRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(404).body("Business profile not found");
        }

        BusinessProfile profile = optional.get();

        // Get interested investors
        List<InterestedInvestorDTO> interestedInvestors = new ArrayList<>();
        for (InvestorSavedBusiness saved : savedBusinessRepository.findByBusiness(profile)) {
            var investor = saved.getInvestor();
            InterestedInvestorDTO dto = new InterestedInvestorDTO();
            dto.setContactName(investor.getContactName());
            dto.setContactPhone(investor.getContactPhone());
            dto.setState(investor.getState());
            interestedInvestors.add(dto);
        }

        // Build DTO
        BusinessProfileDTO dto = new BusinessProfileDTO();
        dto.setId(profile.getId());
        dto.setEmail(profile.getEmail());
        dto.setBusinessName(profile.getBusinessName());
        dto.setIndustry(profile.getIndustry());
        dto.setDescription(profile.getDescription());
        dto.setLogoUrl(profile.getLogoUrl());
        dto.setContactName(profile.getContactName());
        dto.setContactEmail(profile.getContactEmail());
        dto.setContactPhone(profile.getContactPhone());
        dto.setPublished(profile.isPublished());
        dto.setFundingGoal(profile.getFundingGoal());
        dto.setCurrentRevenue(profile.getCurrentRevenue());
        dto.setFoundedDate(profile.getFoundedDate());
        dto.setRunning(profile.isRunning());
        dto.setInterestedInvestors(interestedInvestors);

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<BusinessProfileDTO>> getAllPublished() {
        List<BusinessProfileDTO> dtoList = new ArrayList<>();
        for (BusinessProfile profile : businessProfileRepository.findAll()) {
            if (Boolean.TRUE.equals(profile.isPublished())) {
                BusinessProfileDTO dto = new BusinessProfileDTO();
                dto.setId(profile.getId());
                dto.setEmail(profile.getEmail());
                dto.setBusinessName(profile.getBusinessName());
                dto.setIndustry(profile.getIndustry());
                dto.setDescription(profile.getDescription());
                dto.setLogoUrl(profile.getLogoUrl());
                dtoList.add(dto);
            }
        }

        return ResponseEntity.ok(dtoList);
    }
}