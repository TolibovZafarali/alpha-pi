package com.example.backend.service;

import com.example.backend.dto.BusinessProfileDTO;
import com.example.backend.dto.InvestorProfileDTO;
import com.example.backend.dto.InvestorSavedBusinessDTO;
import com.example.backend.model.BusinessProfile;
import com.example.backend.model.InvestorProfile;
import com.example.backend.model.InvestorSavedBusiness;
import com.example.backend.repository.BusinessProfileRepository;
import com.example.backend.repository.InvestorProfileRepository;
import com.example.backend.repository.InvestorSavedBusinessRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MeService {

    private final BusinessProfileRepository businessRepo;
    private final InvestorProfileRepository investorRepo;
    private final InvestorSavedBusinessRepository savedRepo;

    public BusinessProfileDTO getMyBusiness(Long userId) {
        BusinessProfile bp = businessRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return toDto(bp);
    }

    public BusinessProfileDTO updateMyBusiness(Long userId, BusinessProfileDTO body) {
        BusinessProfile bp = businessRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        fromDto(body, bp);
        return toDto(businessRepo.save(bp));
    }

    public BusinessProfileDTO setPublishMyBusiness(Long userId, boolean value) {
        BusinessProfile bp = businessRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        bp.setPublished(value);
        return toDto(businessRepo.save(bp));
    }

    public InvestorProfileDTO getMyInvestor(Long userId) {
        InvestorProfile ip = investorRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var saved = savedRepo.findByInvestor(ip);
        var dto = toDto(ip);
        dto.setSavedBusinesses(saved.stream().map(this::toDto).collect(Collectors.toList()));
        return dto;
    }

    public InvestorProfileDTO updateMyInvestor(Long userId, InvestorProfileDTO body) {
        InvestorProfile ip = investorRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        fromDto(body, ip);
        return toDto(investorRepo.save(ip));
    }

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

    private InvestorSavedBusinessDTO toDto(InvestorSavedBusiness sb) {
        InvestorSavedBusinessDTO d = new InvestorSavedBusinessDTO();
        d.setId(sb.getId());
        var b = sb.getBusiness();
        if (b != null) {
            d.setBusinessId(b.getId());
            d.setBusinessName(b.getBusinessName());
            d.setIndustry(b.getIndustry());
            d.setDescription(b.getDescription());
            d.setLogoUrl(b.getLogoUrl());
            d.setContactName(b.getContactName());
            d.setContactEmail(b.getContactEmail());
            d.setContactPhone(b.getContactPhone());
            d.setFundingGoal(b.getFundingGoal());
            d.setCurrentRevenue(b.getCurrentRevenue());
            d.setFoundedDate(b.getFoundedDate());
        }
        d.setSavedAt(sb.getSavedAt());
        return d;
    }
}