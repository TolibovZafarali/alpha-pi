package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterestedInvestorDTO {

    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String photoUrl;
    private String state;
}
