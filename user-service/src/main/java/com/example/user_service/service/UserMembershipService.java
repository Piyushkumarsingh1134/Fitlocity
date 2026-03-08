package com.example.user_service.service;

import com.example.user_service.dto.request.UserMembershipRequestDTO;
import com.example.user_service.dto.response.UserMembershipResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserMembershipService {
    UserMembershipResponseDTO createMembership(UserMembershipRequestDTO dto);
    UserMembershipResponseDTO getMembershipById(UUID id);
    List<UserMembershipResponseDTO> getMembershipsByUser(UUID userId);
    List<UserMembershipResponseDTO> getActiveMembershipsByUser(UUID userId);
    UserMembershipResponseDTO updateMembership(UUID id, UserMembershipRequestDTO dto);
    void cancelMembership(UUID id, String reason);
    void deleteMembership(UUID id);
}