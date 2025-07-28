package com.example.backend.repository;

import com.example.backend.model.BusinessProfile;
import com.example.backend.model.InvestorProfile;
import com.example.backend.model.InvestorSavedBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvestorSavedBusinessRepository extends JpaRepository<InvestorSavedBusiness, Long> {
    List<InvestorSavedBusiness> findByInvestor(InvestorProfile investor);
    List<InvestorSavedBusiness> findByBusiness(BusinessProfile business);
    Optional<InvestorSavedBusiness> findByInvestorAndBusiness(InvestorProfile investor, BusinessProfile business);
}
