package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvestorProfileDTO {
    private Long id;
    private Long userId;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String photoUrl;
    private String state;
    private String investmentRange;
    private String interests;

    private List<InvestorSavedBusinessDTO> savedBusinesses;
}
