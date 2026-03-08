package com.example.user_service.service;

import com.example.user_service.dto.request.UserCheckinRequestDTO;
import com.example.user_service.dto.response.UserCheckinResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserCheckinService {
    UserCheckinResponseDTO UserCheckin(UserCheckinRequestDTO dto);
    UserCheckinResponseDTO checkout(UUID UserCheckinId, Integer crowdDensity, Integer equipmentWaitTime);
    UserCheckinResponseDTO getUserCheckinById(UUID id);
    List<UserCheckinResponseDTO> getUserCheckinsByUser(UUID userId);
    List<UserCheckinResponseDTO> getUserCheckinsByGym(UUID gymId);
    long getCurrentOccupancy(UUID gymId);
    void deleteUserCheckin(UUID id);
}