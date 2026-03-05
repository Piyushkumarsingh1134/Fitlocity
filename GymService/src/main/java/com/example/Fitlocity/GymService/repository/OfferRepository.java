package com.fitlocity.gym.repository;

import com.fitlocity.gym.domain.GymOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OfferRepository extends JpaRepository<GymOffer, UUID> {
}
