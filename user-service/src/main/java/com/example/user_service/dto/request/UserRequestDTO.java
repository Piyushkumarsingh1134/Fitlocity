package com.example.user_service.dto.request;

import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private String email;
    private String phone;
    private String password;
    private String fullName;
    private String profilePhotoUrl;
    private LocalDate dateOfBirth;
    private String gender;
    private UUID cityId;
    private String fitnessGoals;
    private String experienceLevel;
    private String dietaryPreferences;
    private String healthConditions;
    private String referralCode;
    private UUID referredBy;
}