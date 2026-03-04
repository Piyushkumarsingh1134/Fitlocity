package com.fitlocity.gym.repository;

import com.fitlocity.gym.domain.GymEquipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GymEquipmentRepository extends JpaRepository<GymEquipment, UUID> {
}
