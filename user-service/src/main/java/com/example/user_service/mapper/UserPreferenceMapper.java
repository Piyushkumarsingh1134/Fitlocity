package com.example.user_service.mapper;

import com.example.user_service.dto.request.UserPreferenceRequestDTO;
import com.example.user_service.dto.response.UserResponsePreferenceDTO;
import com.example.user_service.model.User;
import com.example.user_service.model.UserPreference;
import org.springframework.stereotype.Component;

@Component
public class UserPreferenceMapper {

    public UserPreference toEntity(UserPreferenceRequestDTO dto) {
        if (dto == null) return null;

        User user = User.builder()
                .id(dto.getUserId())
                .build();

        return UserPreference.builder()
                .user(user)
                .preferredCommuteDistanceKm(
                        dto.getPreferredCommuteDistanceKm() != null
                                ? dto.getPreferredCommuteDistanceKm()
                                : 3
                )
                .preferredWorkoutTimeSlots(dto.getPreferredWorkoutTimeSlots())
                .crowdTolerance(dto.getCrowdTolerance())
                .budgetMinMonthly(dto.getBudgetMinMonthly())
                .budgetMaxMonthly(dto.getBudgetMaxMonthly())
                .equipmentPriorities(dto.getEquipmentPriorities())
                .trainerGenderPreference(dto.getTrainerGenderPreference())
                .amenityRequirements(dto.getAmenityRequirements())
                .notificationSettings(dto.getNotificationSettings())
                .privacySettings(dto.getPrivacySettings())
                .build();
    }

    public UserResponsePreferenceDTO toResponseDto(UserPreference entity) {
        if (entity == null) return null;

        return UserResponsePreferenceDTO.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .preferredCommuteDistanceKm(entity.getPreferredCommuteDistanceKm())
                .preferredWorkoutTimeSlots(entity.getPreferredWorkoutTimeSlots())
                .crowdTolerance(entity.getCrowdTolerance())
                .budgetMinMonthly(entity.getBudgetMinMonthly())
                .budgetMaxMonthly(entity.getBudgetMaxMonthly())
                .equipmentPriorities(entity.getEquipmentPriorities())
                .trainerGenderPreference(entity.getTrainerGenderPreference())
                .amenityRequirements(entity.getAmenityRequirements())
                .notificationSettings(entity.getNotificationSettings())
                .privacySettings(entity.getPrivacySettings())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public void updateEntityFromDto(UserPreferenceRequestDTO dto, UserPreference entity) {
        if (dto == null || entity == null) return;

        if (dto.getPreferredCommuteDistanceKm() != null)
            entity.setPreferredCommuteDistanceKm(dto.getPreferredCommuteDistanceKm());

        if (dto.getPreferredWorkoutTimeSlots() != null)
            entity.setPreferredWorkoutTimeSlots(dto.getPreferredWorkoutTimeSlots());

        if (dto.getCrowdTolerance() != null)
            entity.setCrowdTolerance(dto.getCrowdTolerance());

        if (dto.getBudgetMinMonthly() != null)
            entity.setBudgetMinMonthly(dto.getBudgetMinMonthly());

        if (dto.getBudgetMaxMonthly() != null)
            entity.setBudgetMaxMonthly(dto.getBudgetMaxMonthly());

        if (dto.getEquipmentPriorities() != null)
            entity.setEquipmentPriorities(dto.getEquipmentPriorities());

        if (dto.getTrainerGenderPreference() != null)
            entity.setTrainerGenderPreference(dto.getTrainerGenderPreference());

        if (dto.getAmenityRequirements() != null)
            entity.setAmenityRequirements(dto.getAmenityRequirements());

        if (dto.getNotificationSettings() != null)
            entity.setNotificationSettings(dto.getNotificationSettings());

        if (dto.getPrivacySettings() != null)
            entity.setPrivacySettings(dto.getPrivacySettings());
    }
}