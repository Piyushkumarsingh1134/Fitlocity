package com.example.user_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_preferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreference {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "preferred_commute_distance_km")
    @Builder.Default
    private Integer preferredCommuteDistanceKm = 3;

    @Column(name = "preferred_workout_time_slots", columnDefinition = "jsonb")
    private String preferredWorkoutTimeSlots;

    @Column(name = "crowd_tolerance", length = 20)
    private String crowdTolerance;

    @Column(name = "budget_min_monthly", precision = 10, scale = 2)
    private BigDecimal budgetMinMonthly;

    @Column(name = "budget_max_monthly", precision = 10, scale = 2)
    private BigDecimal budgetMaxMonthly;

    @Column(name = "equipment_priorities", columnDefinition = "jsonb")
    private String equipmentPriorities;

    @Column(name = "trainer_gender_preference", length = 10)
    private String trainerGenderPreference;

    @Column(name = "amenity_requirements", columnDefinition = "jsonb")
    private String amenityRequirements;

    @Column(name = "notification_settings", columnDefinition = "jsonb")
    private String notificationSettings;

    @Column(name = "privacy_settings", columnDefinition = "jsonb")
    private String privacySettings;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}