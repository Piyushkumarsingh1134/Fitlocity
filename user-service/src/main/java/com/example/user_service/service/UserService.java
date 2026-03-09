package com.example.user_service.service;

import com.example.user_service.dto.request.UserRequestDTO;
import com.example.user_service.dto.response.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO dto);

    UserResponseDTO getUserById(UUID id);

    UserResponseDTO getUserByEmail(String email);

    UserResponseDTO getUserByPhone(String phone);

    UserResponseDTO updateUser(UUID id, UserRequestDTO dto);

    void deleteUser(UUID id);

    List<UserResponseDTO> getAllUsers();

    List<UserResponseDTO> getUsersByCity(UUID cityId);

    List<UserResponseDTO> getUsersByExperienceLevelAndCity(String level, UUID cityId);

    List<UserResponseDTO> getVerifiedPremiumUsers();

    List<UserResponseDTO> getRecentlyActiveUsers();

    List<UserResponseDTO> getUsersReferredBy(UUID referredById);

    UserResponseDTO verifyUser(UUID id);

    UserResponseDTO upgradeToPremium(UUID id, int durationDays);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}