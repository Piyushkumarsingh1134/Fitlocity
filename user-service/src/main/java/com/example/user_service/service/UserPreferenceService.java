package com.example.user_service.service;

import com.example.user_service.dto.request.UserPreferenceRequestDTO;
import com.example.user_service.dto.response.UserResponsePreferenceDTO;

import java.util.UUID;

public interface UserPreferenceService {

    UserResponsePreferenceDTO createPreference(UserPreferenceRequestDTO requestDto);

    UserResponsePreferenceDTO getPreferenceByUserId(UUID userId);

    UserResponsePreferenceDTO updatePreference(UUID userId, UserPreferenceRequestDTO requestDto);

    void deletePreference(UUID userId);

    boolean preferenceExists(UUID userId);
}