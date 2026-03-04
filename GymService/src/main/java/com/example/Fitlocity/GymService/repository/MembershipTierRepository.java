package com.fitlocity.gym.repository;

import com.fitlocity.gym.domain.GymMembershipTier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MembershipTierRepository extends JpaRepository<GymMembershipTier, UUID> {
}
