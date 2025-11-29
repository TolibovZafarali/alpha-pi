package com.example.backend.mapper;

import com.example.backend.dto.BusinessProfileDTO;
import com.example.backend.model.BusinessProfile;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BusinessProfileMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "interestedInvestors", ignore = true)
    BusinessProfileDTO toDto(BusinessProfile entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(BusinessProfileDTO source, @MappingTarget BusinessProfile target);
}