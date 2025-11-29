package com.example.backend.mapper;

import com.example.backend.dto.InvestorProfileDTO;
import com.example.backend.model.InvestorProfile;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface InvestorProfileMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "savedBusinesses", ignore = true)
    InvestorProfileDTO toDto(InvestorProfile entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(InvestorProfileDTO source, @MappingTarget InvestorProfile target);
}