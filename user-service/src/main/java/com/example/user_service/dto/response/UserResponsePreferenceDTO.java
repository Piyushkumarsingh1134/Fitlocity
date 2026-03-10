package com.example.user_service.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponsePreferenceDTO {

    private UUID id;

    private UUID userId;

    private Integer preferredCommuteDistanceKm;

    private List<String> preferredWorkoutTimeSlots;

    private String crowdTolerance;

    private BigDecimal budgetMinMonthly;

    private BigDecimal budgetMaxMonthly;

    private List<String> equipmentPriorities;

    private String trainerGenderPreference;

    private List<String> amenityRequirements;

    private List<String> notificationSettings;

    private List<String> privacySettings;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}