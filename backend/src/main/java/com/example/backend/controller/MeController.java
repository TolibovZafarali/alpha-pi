package com.example.backend.controller;


import com.example.backend.dto.BusinessProfileDTO;
import com.example.backend.dto.InvestorProfileDTO;
import com.example.backend.security.JwtAuthenticationFilter.JwtUserAuthentication;
import com.example.backend.service.MeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/me")
public class MeController {

    private final MeService meService;

    // === BUSINESS (me) ====

    @GetMapping("/business")
    @PreAuthorize("hasRole('BUSINESS')")
    public ResponseEntity<BusinessProfileDTO> getMyBusiness(Authentication auth) {
        var me = (JwtUserAuthentication) auth;
        return ResponseEntity.ok(meService.getMyBusiness(me.getUserId()));
    }

    @PutMapping("/business")
    @PreAuthorize("hasRole('BUSINESS')")
    public ResponseEntity<BusinessProfileDTO> updateMyBusiness(@RequestBody BusinessProfileDTO body, Authentication auth) {
        var me = (JwtUserAuthentication) auth;
        return ResponseEntity.ok(meService.updateMyBusiness(me.getUserId(), body));
    }

    @PatchMapping("/business/publish")
    @PreAuthorize("hasRole('BUSINESS')")
    public ResponseEntity<BusinessProfileDTO> setPublishMyBusiness(@RequestParam("value") boolean value, Authentication auth) {
        var me = (JwtUserAuthentication) auth;
        return ResponseEntity.ok(meService.setPublishMyBusiness(me.getUserId(), value));
    }

    // === INVESTOR (me) ===

    @GetMapping("/investor")
    @PreAuthorize("hasRole('INVESTOR')")
    public ResponseEntity<InvestorProfileDTO> getMyInvestor(Authentication auth) {
        var me = (JwtUserAuthentication) auth;
        return ResponseEntity.ok(meService.getMyInvestor(me.getUserId()));
    }

    @PutMapping("/investor")
    @PreAuthorize("hasRole('INVESTOR')")
    public ResponseEntity<InvestorProfileDTO> updateMyInvestor(@RequestBody InvestorProfileDTO body, Authentication auth) {
        var me = (JwtUserAuthentication) auth;
        return ResponseEntity.ok(meService.updateMyInvestor(me.getUserId(), body));
    }
}
