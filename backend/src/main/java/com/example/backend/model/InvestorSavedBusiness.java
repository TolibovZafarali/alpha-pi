package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InvestorSavedBusiness {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "investor_id")
    private InvestorProfile investor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "business_id")
    private BusinessProfile business;

    @Column(nullable = false)
    private LocalDateTime savedAt = LocalDateTime.now();
}
