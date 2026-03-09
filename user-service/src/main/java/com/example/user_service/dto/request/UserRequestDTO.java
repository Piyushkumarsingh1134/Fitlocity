package com.example.user_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 255)
    private String email;

    @Size(max = 20)
    private String phone;

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Size(max = 100)
    private String fullName;

    private String profilePhotoUrl;

    private LocalDate dateOfBirth;

    @Size(max = 20)
    private String gender;

    private UUID cityId;

    private String homeLocation;

    private String workLocation;

    // JSON string representing fitness goals
    private String fitnessGoals;

    @Size(max = 20)
    private String experienceLevel;

    // JSON string representing dietary preferences
    private String dietaryPreferences;

    // JSON string representing health conditions
    private String healthConditions;

    private String referralCode;

    private UUID referredById;
}