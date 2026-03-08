package com.example.user_service.service;

import com.example.user_service.dto.request.UserPreferenceRequestDTO;
import com.example.user_service.dto.response.UserPreferenceResponseDTO;

import java.util.UUID;

public interface UserPreferenceService {
    UserPreferenceResponseDTO createPreference(UserPreferenceRequestDTO dto);
    UserPreferenceResponseDTO getPreferenceByUserId(UUID userId);
    UserPreferenceResponseDTO updatePreference(UUID userId, UserPreferenceRequestDTO dto);
    void deletePreference(UUID userId);
}