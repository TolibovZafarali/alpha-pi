package com.example.backend.repository;

import com.example.backend.model.BusinessProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessProfileRepository extends JpaRepository<BusinessProfile, Long> {
    Optional<BusinessProfile> findByUserId(Long userId);
    List<BusinessProfile> findAllByPublishedTrue();
}
