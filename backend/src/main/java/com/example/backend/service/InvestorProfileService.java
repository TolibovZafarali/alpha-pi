package com.example.backend.service;

import com.example.backend.dto.InvestorProfileDTO;
import com.example.backend.mapper.InvestorProfileMapper;
import com.example.backend.model.InvestorProfile;
import com.example.backend.repository.InvestorProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class InvestorProfileService {

    private final InvestorProfileRepository investorRepo;
    private final InvestorProfileMapper investorMapper;

    public InvestorProfileService(InvestorProfileRepository investorRepo, InvestorProfileMapper investorMapper) {
        this.investorRepo = investorRepo;
        this.investorMapper = investorMapper;
    }

    public Optional<InvestorProfileDTO> getByProfileId(Long profileId) {
        return investorRepo.findById(profileId).map(investorMapper::toDto);
    }

    public InvestorProfileDTO getInvestorByUserIdOrThrow(Long userId) {
        InvestorProfile ip = investorRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return investorMapper.toDto(ip);
    }

    public InvestorProfileDTO updateInvestorForUser(Long userId, InvestorProfileDTO body) {
        InvestorProfile ip = investorRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        investorMapper.updateEntity(body, ip);
        return investorMapper.toDto(investorRepo.save(ip));
    }
}