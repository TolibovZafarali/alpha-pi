package com.example.backend.mapper;

import com.example.backend.dto.InvestorSavedBusinessDTO;
import com.example.backend.model.InvestorSavedBusiness;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvestorSavedBusinessMapper {

    @Mapping(target = "businessId", source = "business.id")
    @Mapping(target = "businessName", source = "business.businessName")
    @Mapping(target = "industry", source = "business.industry")
    @Mapping(target = "description", source = "business.description")
    @Mapping(target = "logoUrl", source = "business.logoUrl")
    @Mapping(target = "contactName", source = "business.contactName")
    @Mapping(target = "contactEmail", source = "business.contactEmail")
    @Mapping(target = "contactPhone", source = "business.contactPhone")
    @Mapping(target = "fundingGoal", source = "business.fundingGoal")
    @Mapping(target = "currentRevenue", source = "business.currentRevenue")
    @Mapping(target = "foundedDate", source = "business.foundedDate")
    InvestorSavedBusinessDTO toDto(InvestorSavedBusiness savedBusiness);
}