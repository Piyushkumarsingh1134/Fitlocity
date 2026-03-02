package com.fitlocity.gym.repository;

import com.fitlocity.gym.domain.GymOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GymOwnerRepository extends JpaRepository<GymOwner, UUID> {

    Optional<GymOwner> findByUserId(UUID userId);

    boolean existsByUserId(UUID userId);
}