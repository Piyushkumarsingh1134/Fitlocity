package com.fitlocity.gym.repository;

import com.fitlocity.gym.domain.GymLeadAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GymAnalyticsRepository extends JpaRepository<GymLeadAnalytics, UUID> {
}
