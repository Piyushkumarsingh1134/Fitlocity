package com.example.user_service.repository;

import com.example.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    Optional<User> findByReferralCode(String referralCode);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    List<User> findByReferredBy(UUID referredBy);

    List<User> findByCityId(UUID cityId);

    @Query("SELECT u FROM User u WHERE u.isVerified = true AND u.isPremium = true")
    List<User> findAllVerifiedPremiumUsers();

    @Query("SELECT u FROM User u WHERE u.experienceLevel = :level AND u.cityId = :cityId")
    List<User> findByExperienceLevelAndCity(@Param("level") String level, @Param("cityId") UUID cityId);

    @Query("SELECT u FROM User u WHERE u.lastActive >= CURRENT_TIMESTAMP - 7 * 24 * 60 * 60")
    List<User> findRecentlyActiveUsers();
}