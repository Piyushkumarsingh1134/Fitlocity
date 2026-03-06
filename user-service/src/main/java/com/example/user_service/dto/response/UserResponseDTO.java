package com.example.user_service.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private UUID id;
    private String email;
    private String phone;
    private String fullName;
    private String profilePhotoUrl;
    private LocalDate dateOfBirth;
    private String gender;
    private UUID cityId;
    private String fitnessGoals;
    private String experienceLevel;
    private String dietaryPreferences;
    private String healthConditions;
    private Boolean isVerified;
    private Boolean isPremium;
    private LocalDateTime premiumExpiry;
    private BigDecimal credibilityScore;
    private LocalDateTime createdAt;
    private LocalDateTime lastActive;
    private String referralCode;
}