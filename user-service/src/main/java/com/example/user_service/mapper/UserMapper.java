package com.example.user_service.mapper;

import com.example.user_service.dto.request.UserRequestDTO;
import com.example.user_service.dto.response.UserResponseDTO;
import com.example.user_service.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO dto) {
        if (dto == null) return null;

        User.UserBuilder builder = User.builder()
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .fullName(dto.getFullName())
                .profilePhotoUrl(dto.getProfilePhotoUrl())
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender())
                .cityId(dto.getCityId())
                .fitnessGoals(dto.getFitnessGoals())
                .experienceLevel(dto.getExperienceLevel())
                .dietaryPreferences(dto.getDietaryPreferences())
                .healthConditions(dto.getHealthConditions())
                .referralCode(dto.getReferralCode());

        // referredBy is resolved in service layer via referredById
        return builder.build();
    }

    /**
     * Updates an existing User entity from a UserRequestDTO (for PATCH/PUT operations).
     * Only non-null fields are applied.
     */
    public void updateEntityFromDTO(UserRequestDTO dto, User user) {
        if (dto == null || user == null) return;

        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPhone() != null) user.setPhone(dto.getPhone());
        if (dto.getFullName() != null) user.setFullName(dto.getFullName());
        if (dto.getProfilePhotoUrl() != null) user.setProfilePhotoUrl(dto.getProfilePhotoUrl());
        if (dto.getDateOfBirth() != null) user.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getGender() != null) user.setGender(dto.getGender());
        if (dto.getCityId() != null) user.setCityId(dto.getCityId());
        if (dto.getFitnessGoals() != null) user.setFitnessGoals(dto.getFitnessGoals());
        if (dto.getExperienceLevel() != null) user.setExperienceLevel(dto.getExperienceLevel());
        if (dto.getDietaryPreferences() != null) user.setDietaryPreferences(dto.getDietaryPreferences());
        if (dto.getHealthConditions() != null) user.setHealthConditions(dto.getHealthConditions());
    }

    /**
     * Maps a User entity to a UserResponseDTO.
     */
    public UserResponseDTO toResponseDTO(User user) {
        if (user == null) return null;

        UserResponseDTO.UserResponseDTOBuilder builder = UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .fullName(user.getFullName())
                .profilePhotoUrl(user.getProfilePhotoUrl())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .cityId(user.getCityId())
                .fitnessGoals(user.getFitnessGoals())
                .experienceLevel(user.getExperienceLevel())
                .dietaryPreferences(user.getDietaryPreferences())
                .healthConditions(user.getHealthConditions())
                .isVerified(user.getIsVerified())
                .isPremium(user.getIsPremium())
                .premiumExpiry(user.getPremiumExpiry())
                .credibilityScore(user.getCredibilityScore())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .lastActive(user.getLastActive())
                .referralCode(user.getReferralCode());

        // Flatten referredBy to avoid circular references in JSON
        if (user.getReferredBy() != null) {
            builder.referredById(user.getReferredBy().getId());
            builder.referredByName(user.getReferredBy().getFullName());
        }

        return builder.build();
    }
}