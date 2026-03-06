package com.fitlocity.gym.repository;

import com.fitlocity.gym.domain.GymLeadAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GymAnalyticsRepository extends JpaRepository<GymLeadAnalytics, UUID> {
    List<GymLeadAnalytics> findByGymIdOrderByDateDesc(UUID gymId);
    Optional<GymLeadAnalytics> findByGymIdAndDate(UUID gymId, LocalDate date);
}
