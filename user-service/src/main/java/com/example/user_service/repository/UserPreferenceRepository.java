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

    @Query("SELECT up FROM UserPreference up WHERE up.user.id = :userId")
    Optional<UserPreference> findByUserIdWithDetails(@Param("userId") UUID userId);

    List<UserPreference> findByCrowdTolerance(String crowdTolerance);

    @Query("SELECT up FROM UserPreference up WHERE up.preferredCommuteDistanceKm <= :maxDistance")
    List<UserPreference> findByPreferredCommuteDistanceKmLessThanEqual(@Param("maxDistance") Integer maxDistance);

    @Query("SELECT up FROM UserPreference up WHERE up.budgetMaxMonthly >= :budget AND up.budgetMinMonthly <= :budget")
    List<UserPreference> findByBudgetRange(@Param("budget") java.math.BigDecimal budget);

    List<UserPreference> findByTrainerGenderPreference(String trainerGenderPreference);
}