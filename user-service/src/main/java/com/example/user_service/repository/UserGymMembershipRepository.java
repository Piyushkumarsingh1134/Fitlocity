package com.example.user_service.repository;

import com.example.user_service.model.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserGymMembershipRepository extends JpaRepository<UserMembership, UUID> {

    List<UserMembership> findByUser_Id(UUID userId);

    List<UserMembership> findByGymId(UUID gymId);

    List<UserMembership> findByUser_IdAndIsActiveTrue(UUID userId);

    Optional<UserMembership> findByUser_IdAndGymIdAndIsActiveTrue(UUID userId, UUID gymId);

    boolean existsByUser_IdAndGymIdAndIsActiveTrue(UUID userId, UUID gymId);

    @Query("SELECT m FROM UserMembership m WHERE m.endDate < :today AND m.isActive = true")
    List<UserMembership> findExpiredActiveMemberships(@Param("today") LocalDate today);

    @Query("SELECT m FROM UserMembership m WHERE m.endDate BETWEEN :today AND :soon AND m.autoRenew = false")
    List<UserMembership> findMembershipsExpiringSoon(@Param("today") LocalDate today,
                                                        @Param("soon") LocalDate soon);

    @Query("SELECT m FROM UserMembership m WHERE m.gymId = :gymId AND m.isActive = true")
    List<UserMembership> findActiveMembersByGym(@Param("gymId") UUID gymId);

    @Query("SELECT COUNT(m) FROM UserMembership m WHERE m.gymId = :gymId AND m.isActive = true")
    long countActiveMembersByGym(@Param("gymId") UUID gymId);
}