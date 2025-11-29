package com.example.backend.service;

import com.example.backend.dto.InvestorProfileDTO;
import com.example.backend.model.InvestorProfile;
import com.example.backend.repository.InvestorProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@AllArgsConstructor
@Service
public class InvestorProfileService {

    private final InvestorProfileRepository investorRepo;

    public Optional<InvestorProfileDTO> getByProfileId(Long profileId) {
        return investorRepo.findById(profileId).map(this::toDto);
    }

    public InvestorProfileDTO getInvestorByUserIdOrThrow(Long userId) {
        InvestorProfile ip = investorRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return toDto(ip);
    }

    public InvestorProfileDTO updateInvestorForUser(Long userId, InvestorProfileDTO body) {
        InvestorProfile ip = investorRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        fromDto(body, ip);
        return toDto(investorRepo.save(ip));
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