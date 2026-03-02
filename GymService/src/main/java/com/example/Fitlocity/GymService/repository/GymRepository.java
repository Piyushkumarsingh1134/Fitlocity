package com.fitlocity.gym.repository;

import com.fitlocity.gym.domain.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GymRepository extends JpaRepository<Gym, UUID> {

    Optional<Gym> findBySlug(String slug);

    boolean existsBySlug(String slug);
}