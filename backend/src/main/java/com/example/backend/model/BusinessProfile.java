package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BusinessProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_business_profile_user")
    )
    private User user;

    private String businessName;
    private String industry;
    private String description;
    private String logoUrl;

    private String contactName;
    private String contactEmail;
    private String contactPhone;

    private Boolean isPublished = false;
    private Double fundingGoal;
    private Double currentRevenue;
    private LocalDate foundedDate;

    // Investors who saved this business
    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<InvestorSavedBusiness> interestedInvestors;
}
