package com.example.backend.service;

import com.example.backend.dto.BusinessProfileDTO;
import com.example.backend.dto.InvestorProfileDTO;
import com.example.backend.mapper.BusinessProfileMapper;
import com.example.backend.mapper.InvestorProfileMapper;
import com.example.backend.mapper.InvestorSavedBusinessMapper;
import com.example.backend.model.BusinessProfile;
import com.example.backend.model.InvestorProfile;
import com.example.backend.repository.BusinessProfileRepository;
import com.example.backend.repository.InvestorProfileRepository;
import com.example.backend.repository.InvestorSavedBusinessRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MeService {

    private final BusinessProfileRepository businessRepo;
    private final InvestorProfileRepository investorRepo;
    private final InvestorSavedBusinessRepository savedRepo;
    private final BusinessProfileMapper businessMapper;
    private final InvestorProfileMapper investorMapper;
    private final InvestorSavedBusinessMapper savedBusinessMapper;

    public MeService(
            BusinessProfileRepository businessRepo,
            InvestorProfileRepository investorRepo,
            InvestorSavedBusinessRepository savedRepo,
            BusinessProfileMapper businessMapper,
            InvestorProfileMapper investorMapper,
            InvestorSavedBusinessMapper savedBusinessMapper
    ) {
        this.businessRepo = businessRepo;
        this.investorRepo = investorRepo;
        this.savedRepo = savedRepo;
        this.businessMapper = businessMapper;
        this.investorMapper = investorMapper;
        this.savedBusinessMapper = savedBusinessMapper;
    }

    public BusinessProfileDTO getMyBusiness(Long userId) {
        BusinessProfile bp = businessRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return businessMapper.toDto(bp);
    }

    public BusinessProfileDTO updateMyBusiness(Long userId, BusinessProfileDTO body) {
        BusinessProfile bp = businessRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        businessMapper.updateEntity(body, bp);
        return businessMapper.toDto(businessRepo.save(bp));
    }

    public BusinessProfileDTO setPublishMyBusiness(Long userId, boolean value) {
        BusinessProfile bp = businessRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        bp.setPublished(value);
        return businessMapper.toDto(businessRepo.save(bp));
    }

    public InvestorProfileDTO getMyInvestor(Long userId) {
        InvestorProfile ip = investorRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var saved = savedRepo.findByInvestor(ip);
        var dto = investorMapper.toDto(ip);
        dto.setSavedBusinesses(saved.stream().map(savedBusinessMapper::toDto).toList());
        return dto;
    }

    public InvestorProfileDTO updateMyInvestor(Long userId, InvestorProfileDTO body) {
        InvestorProfile ip = investorRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        investorMapper.updateEntity(body, ip);
        return investorMapper.toDto(investorRepo.save(ip));
    }
}