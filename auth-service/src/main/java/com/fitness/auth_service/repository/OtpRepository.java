package com.fitness.auth_service.repository;

import com.fitness.auth_service.model.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpVerification, String> {
    Optional<OtpVerification> findTopByPhoneAndIsUsedFalseOrderByCreatedAtDesc(String phone);
}