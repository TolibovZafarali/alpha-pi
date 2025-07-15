package com.example.backend.repository;

import com.example.backend.model.PotentialLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PotentialLocationRepository extends JpaRepository<PotentialLocation, Long> {
}
