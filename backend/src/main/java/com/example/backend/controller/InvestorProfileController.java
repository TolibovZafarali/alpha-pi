package com.example.backend.controller;

import com.example.backend.dto.InvestorProfileDTO;
import com.example.backend.model.InvestorProfile;
import com.example.backend.model.User;
import com.example.backend.repository.InvestorProfileRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/investors")
public class InvestorProfileController {

    private final InvestorProfileRepository investorRepo;
    private final UserRepository userRepo;

    public InvestorProfileController(InvestorProfileRepository investorRepo, UserRepository userRepo) {
        this.investorRepo = investorRepo;
        this.userRepo = userRepo;
    }

    // Fetch the investor profile attached to a given user id
    @GetMapping("{userId}")
    public ResponseEntity<InvestorProfileDTO> getByUserId(@PathVariable Long userId) {
        Optional<InvestorProfile> ip = investorRepo.findByUserId(userId);
        return ip.map(investorProfile -> ResponseEntity.ok(toDto(investorProfile)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Fetch by profile id
    @GetMapping("/profile/{profileId}")
    public ResponseEntity<InvestorProfileDTO> getByProfileId(@PathVariable Long profileId) {
        Optional<InvestorProfile> ip = investorRepo.findById(profileId);
        return ip.map(investorProfile -> ResponseEntity.ok(toDto(investorProfile)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Create (or upsert) investor profile for a user
    @PostMapping("/{userId}")
    public ResponseEntity<InvestorProfileDTO> createOrUpsert(@PathVariable Long userId, @RequestBody InvestorProfileDTO payload) {
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        InvestorProfile entity = investorRepo.findByUserId(userId).orElseGet(InvestorProfile::new);
        entity.setUser(user);
        fromDto(payload, entity);

        InvestorProfile saved = investorRepo.save(entity);
        return ResponseEntity.status(HttpStatus.OK).body(toDto(saved));
    }

    // Update investor profile by user id
    @PutMapping("{userId}")
    public ResponseEntity<InvestorProfileDTO> update(@PathVariable Long userId, @RequestBody InvestorProfileDTO payload) {
        Optional<InvestorProfile> existingOpt = investorRepo.findByUserId(userId);
        if (existingOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        InvestorProfile existing = existingOpt.get();
        fromDto(payload, existing);
        InvestorProfile saved = investorRepo.save(existing);
        return ResponseEntity.ok(toDto(saved));
    }

    private InvestorProfileDTO toDto(InvestorProfile e) {
        InvestorProfileDTO d = new InvestorProfileDTO();
        d.setId(e.getId());
        d.setUserId(e.getUser() != null ? e.getUser().getId() : null);

        d.setContactName(e.getContactName());
        d.setContactEmail(e.getContactEmail());
        d.setContactPhone(e.getContactPhone());
        d.setState(e.getState());
        d.setInterests(e.getInterests());
        d.setInvestmentRange(e.getInvestmentRange());

        return d;
    }

    private void fromDto(InvestorProfileDTO d, InvestorProfile e) {
        if (d.getContactName() != null) e.setContactName(d.getContactName());
        if (d.getContactEmail() != null) e.setContactEmail(d.getContactEmail());
        if (d.getContactPhone() != null) e.setContactPhone(d.getContactPhone());
        if (d.getState() != null) e.setState(d.getState());
        if (d.getInterests() != null) e.setInterests(d.getInterests());
        if (d.getInvestmentRange() != null) e.setInvestmentRange(d.getInvestmentRange());
    }
}
