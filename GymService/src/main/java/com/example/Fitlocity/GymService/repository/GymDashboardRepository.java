package com.fitlocity.gym.repository;

import com.fitlocity.gym.domain.GymDashboardPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GymDashboardRepository extends JpaRepository<GymDashboardPreference, UUID> {
    Optional<GymDashboardPreference> findByGymId(UUID gymId);
}
