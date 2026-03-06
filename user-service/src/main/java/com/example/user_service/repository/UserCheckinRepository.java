package com.example.user_service.repository;

import com.example.user_service.model.UserCheckin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserCheckinRepository extends JpaRepository<UserCheckin, UUID> {

    List<UserCheckin> findByUserId(UUID userId);

    List<UserCheckin> findByGymId(UUID gymId);

    List<UserCheckin> findByMembershipId(UUID membershipId);

    // Find active checkin (checked in but not yet checked out)
    Optional<UserCheckin> findByUserIdAndCheckoutTimeIsNull(UUID userId);

    @Query("SELECT c FROM UserCheckin c WHERE c.gymId = :gymId AND c.checkoutTime IS NULL")
    List<UserCheckin> findCurrentlyCheckedInByGym(@Param("gymId") UUID gymId);

    @Query("SELECT c FROM UserCheckin c WHERE c.userId = :userId AND c.checkinTime BETWEEN :from AND :to")
    List<UserCheckin> findByUserIdAndDateRange(@Param("userId") UUID userId,
                                              @Param("from") LocalDateTime from,
                                              @Param("to") LocalDateTime to);

    @Query("SELECT AVG(c.durationMinutes) FROM UserCheckin c WHERE c.gymId = :gymId")
    Double findAvgDurationByGym(@Param("gymId") UUID gymId);

    @Query("SELECT AVG(c.crowdDensityObserved) FROM UserCheckin c WHERE c.gymId = :gymId AND c.checkinTime >= :since")
    Double findAvgCrowdDensityByGym(@Param("gymId") UUID gymId, @Param("since") LocalDateTime since);

    @Query("SELECT COUNT(c) FROM UserCheckin c WHERE c.gymId = :gymId AND c.checkoutTime IS NULL")
    long countCurrentOccupancyByGym(@Param("gymId") UUID gymId);
}