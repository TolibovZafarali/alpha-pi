package com.example.backend.controller;


import com.example.backend.dto.BusinessProfileDTO;
import com.example.backend.dto.InvestorProfileDTO;
import com.example.backend.model.BusinessProfile;
import com.example.backend.model.InvestorProfile;
import com.example.backend.repository.BusinessProfileRepository;
import com.example.backend.repository.InvestorProfileRepository;
import com.example.backend.security.JwtAuthenticationFilter.JwtUserAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/me")
public class MeController {

    private final BusinessProfileRepository businessRepo;
    private final InvestorProfileRepository investorRepo;

    public MeController(BusinessProfileRepository businessRepo, InvestorProfileRepository investorRepo) {
        this.businessRepo = businessRepo;
        this.investorRepo = investorRepo;
    }

    // === BUSINESS (me) ====

    @GetMapping("/business")
    @PreAuthorize("hasRole('BUSINESS')")
    public ResponseEntity<BusinessProfileDTO> getMyBusiness(Authentication auth) {
        var me = (JwtUserAuthentication) auth;
        var bp = businessRepo.findByUserId(me.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(toDto(bp));
    }

    @PutMapping("/business")
    @PreAuthorize("hasRole('BUSINESS')")
    public ResponseEntity<BusinessProfileDTO> updateMyBusiness(@RequestBody BusinessProfileDTO body, Authentication auth) {
        var me = (JwtUserAuthentication) auth;
        var bp = businessRepo.findByUserId(me.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        fromDto(body, bp);
        return ResponseEntity.ok(toDto(businessRepo.save(bp)));
    }

    @PatchMapping("/business/publish")
    @PreAuthorize("hasRole('BUSINESS')")
    public ResponseEntity<BusinessProfileDTO> setPublishMyBusiness(@RequestParam("value") boolean value, Authentication auth) {
        var me = (JwtUserAuthentication) auth;
        var bp = businessRepo.findByUserId(me.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        bp.setPublished(value);
        return ResponseEntity.ok(toDto(businessRepo.save(bp)));
    }

    // === INVESTOR (me) ===

    @GetMapping("/investor")
    @PreAuthorize("hasRole('INVESTOR')")
    public ResponseEntity<InvestorProfileDTO> getMyInvestor(Authentication auth) {
        var me = (JwtUserAuthentication) auth;
        var ip = investorRepo.findByUserId(me.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(toDto(ip));
    }

    @PutMapping("/investor")
    @PreAuthorize("hasRole('INVESTOR')")
    public ResponseEntity<InvestorProfileDTO> updateMyInvestor(@RequestBody InvestorProfileDTO body, Authentication auth) {
        var me = (JwtUserAuthentication) auth;
        var ip = investorRepo.findByUserId(me.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        fromDto(body, ip);
        return ResponseEntity.ok(toDto(investorRepo.save(ip)));
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
        d.setPhotoUrl(e.getPhotoUrl());
        return d;
    }

    private void fromDto(InvestorProfileDTO d, InvestorProfile e) {
        if (d.getContactName() != null) e.setContactName(d.getContactName());
        if (d.getContactEmail() != null) e.setContactEmail(d.getContactEmail());
        if (d.getContactPhone() != null) e.setContactPhone(d.getContactPhone());
        if (d.getState() != null) e.setState(d.getState());
        if (d.getInterests() != null) e.setInterests(d.getInterests());
        if (d.getInvestmentRange() != null) e.setInvestmentRange(d.getInvestmentRange());
        if (d.getPhotoUrl() != null) e.setPhotoUrl(d.getPhotoUrl());
    }
}
