package com.example.backend.service;

import com.example.backend.dto.BusinessProfileDTO;
import com.example.backend.model.BusinessProfile;
import com.example.backend.repository.BusinessProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BusinessProfileService {

    private final BusinessProfileRepository businessRepo;

    public List<BusinessProfileDTO> getAllPublished() {
        List<BusinessProfile> published = businessRepo.findAllByIsPublishedTrue();
        return published.stream().map(this::toDto).toList();
    }

    public Optional<BusinessProfileDTO> getByProfileId(Long profileId) {
        return businessRepo.findById(profileId).map(this::toDto);
    }

    public BusinessProfileDTO getBusinessByUserIdOrThrow(Long userId) {
        BusinessProfile bp = businessRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return toDto(bp);
    }

    public BusinessProfileDTO updateBusinessForUser(Long userId, BusinessProfileDTO body) {
        BusinessProfile bp = businessRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        fromDto(body, bp);
        return toDto(businessRepo.save(bp));
    }

    public BusinessProfileDTO setPublishStatus(Long userId, boolean value) {
        BusinessProfile bp = businessRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        bp.setPublished(value);
        return toDto(businessRepo.save(bp));
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
}