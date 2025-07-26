package com.example.backend.dto;

public class InterestedInvestorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String photoUrl;
    private String businessEmail;
    private String businessPhone;
    private String state;

    public InterestedInvestorDTO() {
    }

    public InterestedInvestorDTO(Long id, String firstName, String lastName, String photoUrl,
                                 String businessEmail, String businessPhone, String state) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoUrl = photoUrl;
        this.businessEmail = businessEmail;
        this.businessPhone = businessPhone;
        this.state = state;
    }
}
