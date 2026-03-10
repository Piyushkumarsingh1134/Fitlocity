package com.example.user_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferenceRequestDTO {

    private UUID userId;

    @Min(value = 1, message = "Commute distance must be at least 1 km")
    @Max(value = 50, message = "Commute distance must not exceed 50 km")
    private Integer preferredCommuteDistanceKm;

    private List<String> preferredWorkoutTimeSlots;

    @Size(max = 20, message = "Crowd tolerance must not exceed 20 characters")
    private String crowdTolerance;

    @DecimalMin(value = "0.0", inclusive = false, message = "Minimum budget must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Invalid budget format")
    private BigDecimal budgetMinMonthly;

    @DecimalMin(value = "0.0", inclusive = false, message = "Maximum budget must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Invalid budget format")
    private BigDecimal budgetMaxMonthly;

    private List<String> equipmentPriorities;

    @Size(max = 10, message = "Trainer gender preference must not exceed 10 characters")
    private String trainerGenderPreference;

    private List<String> amenityRequirements;

    private List<String> notificationSettings;

    private List<String> privacySettings;
}