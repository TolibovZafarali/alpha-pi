package com.example.backend.repository;

import com.example.backend.model.BusinessProfile;
import com.example.backend.model.InvestorProfile;
import com.example.backend.model.InvestorSavedBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestorSavedBusinessRepository extends JpaRepository<InvestorSavedBusiness, Long> {
    boolean existsByInvestorProfileAndBusinessProfile(InvestorProfile investor, BusinessProfile business);
    Optional<InvestorSavedBusiness> findByInvestorProfileAndBusinessProfile(InvestorProfile investor, BusinessProfile business);

}
