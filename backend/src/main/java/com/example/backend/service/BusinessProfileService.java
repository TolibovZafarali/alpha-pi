package com.example.backend.service;

import com.example.backend.dto.BusinessProfileDTO;
import com.example.backend.mapper.BusinessProfileMapper;
import com.example.backend.model.BusinessProfile;
import com.example.backend.repository.BusinessProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessProfileService {

    private final BusinessProfileRepository businessRepo;
    private final BusinessProfileMapper businessMapper;

    public BusinessProfileService(BusinessProfileRepository businessRepo, BusinessProfileMapper businessMapper) {
        this.businessRepo = businessRepo;
        this.businessMapper = businessMapper;
    }

    public List<BusinessProfileDTO> getAllPublished() {
        List<BusinessProfile> published = businessRepo.findAllByIsPublishedTrue();
        return published.stream().map(businessMapper::toDto).toList();
    }

    public Optional<BusinessProfileDTO> getByProfileId(Long profileId) {
        return businessRepo.findById(profileId).map(businessMapper::toDto);
    }

    public BusinessProfileDTO getBusinessByUserIdOrThrow(Long userId) {
        BusinessProfile bp = businessRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return businessMapper.toDto(bp);
    }

    public BusinessProfileDTO updateBusinessForUser(Long userId, BusinessProfileDTO body) {
        BusinessProfile bp = businessRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        businessMapper.updateEntity(body, bp);
        return businessMapper.toDto(businessRepo.save(bp));
    }

    public BusinessProfileDTO setPublishStatus(Long userId, boolean value) {
        BusinessProfile bp = businessRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        bp.setPublished(value);
        return businessMapper.toDto(businessRepo.save(bp));
    }
}