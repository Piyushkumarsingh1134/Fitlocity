package com.example.user_service.repository;

import com.example.user_service.model.UserPremiumSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPremiumSubscriptionRepository extends JpaRepository<UserPremiumSubscription, UUID> {

    List<UserPremiumSubscription> findByUserId(UUID userId);

    Optional<UserPremiumSubscription> findByUserIdAndStatus(UUID userId, String status);

    boolean existsByUserIdAndStatus(UUID userId, String status);

    List<UserPremiumSubscription> findByPlanId(UUID planId);

    @Query("SELECT s FROM UserPremiumSubscription s WHERE s.userId = :userId AND s.status = 'ACTIVE'")
    Optional<UserPremiumSubscription> findActiveSubscriptionByUser(@Param("userId") UUID userId);

    @Query("SELECT s FROM UserPremiumSubscription s WHERE s.endDate < :today AND s.status = 'ACTIVE'")
    List<UserPremiumSubscription> findExpiredActiveSubscriptions(@Param("today") LocalDate today);

    @Query("SELECT s FROM UserPremiumSubscription s WHERE s.endDate BETWEEN :today AND :soon AND s.autoRenew = false AND s.status = 'ACTIVE'")
    List<UserPremiumSubscription> findSubscriptionsExpiringSoon(@Param("today") LocalDate today,
                                                                @Param("soon") LocalDate soon);

    @Query("SELECT COUNT(s) FROM UserPremiumSubscription s WHERE s.status = 'ACTIVE'")
    long countAllActiveSubscriptions();
}