package com.example.backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InvestorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_investor_profile_user")
    )
    private User user;

    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String photoUrl;
    private String state;
    private String investmentRange;

    @Column(columnDefinition = "TEXT")
    private String interests;

    // List of saved businesses
    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<InvestorSavedBusiness> savedBusinesses;
}
