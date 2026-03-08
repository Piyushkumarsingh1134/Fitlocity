package com.example.user_service.service;

import com.example.user_service.dto.request.UserPremiumSubscriptionRequestDTO;
import com.example.user_service.dto.response.UserPremiumSubscriptionResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserPremiumSubscriptionService {
    UserPremiumSubscriptionResponseDTO createSubscription(UserPremiumSubscriptionRequestDTO dto);
    UserPremiumSubscriptionResponseDTO getSubscriptionById(UUID id);
    UserPremiumSubscriptionResponseDTO getActiveSubscriptionByUser(UUID userId);
    List<UserPremiumSubscriptionResponseDTO> getAllSubscriptionsByUser(UUID userId);
    UserPremiumSubscriptionResponseDTO updateSubscription(UUID id, UserPremiumSubscriptionRequestDTO dto);
    void cancelSubscription(UUID id);
    void deleteSubscription(UUID id);
}