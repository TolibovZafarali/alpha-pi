package com.example.backend.controller;

import com.example.backend.dto.InvestorProfileDTO;
import com.example.backend.model.InvestorProfile;
import com.example.backend.repository.InvestorProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/investors")
public class InvestorProfileController {

    private final InvestorProfileRepository investorRepo;

    public InvestorProfileController(InvestorProfileRepository investorRepo) {
        this.investorRepo = investorRepo;
    }


    // Fetch by profile id
    @GetMapping("/profile/{profileId}")
    public ResponseEntity<InvestorProfileDTO> getByProfileId(@PathVariable Long profileId) {
        Optional<InvestorProfile> ip = investorRepo.findById(profileId);
        return ip.map(investorProfile -> ResponseEntity.ok(toDto(investorProfile)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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
}
