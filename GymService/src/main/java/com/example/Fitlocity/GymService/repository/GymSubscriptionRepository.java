package com.fitlocity.gym.repository;

import com.fitlocity.gym.domain.GymOsSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GymSubscriptionRepository extends JpaRepository<GymOsSubscription, UUID> {
    Optional<GymOsSubscription> findByGymIdAndStatus(UUID gymId, String status);
}
