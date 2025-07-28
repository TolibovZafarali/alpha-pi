package com.example.backend.dto;

import java.util.List;

public class InvestorProfileDTO {
    private Long id;
    private String email;
    private String name;
    private String contactName;
    private String contactPhone;
    private String state;
    private String investmentRange;
    private String interests;

    private List<InvestorSavedBusinessDTO> savedBusinesses;
}
