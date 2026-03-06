package com.example.user_service.repository;

import com.example.user_service.model.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, UUID> {

    Optional<UserPreference> findByUserId(UUID userId);

    boolean existsByUserId(UUID userId);

    void deleteByUserId(UUID userId);

    @Query("SELECT p FROM UserPreference p WHERE p.preferredCommuteDistanceKm <= :maxKm")
    List<UserPreference> findByMaxCommuteDistance(@Param("maxKm") Integer maxKm);

    @Query("SELECT p FROM UserPreference p WHERE p.budgetMinMonthly >= :min AND p.budgetMaxMonthly <= :max")
    List<UserPreference> findByBudgetRange(@Param("min") java.math.BigDecimal min,
                                           @Param("max") java.math.BigDecimal max);

    @Query("SELECT p FROM UserPreference p WHERE p.crowdTolerance = :tolerance")
    List<UserPreference> findByCrowdTolerance(@Param("tolerance") String tolerance);
}