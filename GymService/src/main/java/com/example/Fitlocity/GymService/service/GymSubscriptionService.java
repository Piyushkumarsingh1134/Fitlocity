package com.fitlocity.gym.service;

import com.fitlocity.gym.domain.Gym;
import com.fitlocity.gym.domain.GymOsSubscription;
import com.fitlocity.gym.dto.request.CreateSubscriptionRequest;
import com.fitlocity.gym.dto.response.SubscriptionResponse;
import com.fitlocity.gym.exception.BadRequestException;
import com.fitlocity.gym.exception.ResourceNotFoundException;
import com.fitlocity.gym.repository.GymRepository;
import com.fitlocity.gym.repository.GymSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GymSubscriptionService {

    private final GymSubscriptionRepository subscriptionRepository;
    private final GymRepository gymRepository;

    public SubscriptionResponse createSubscription(CreateSubscriptionRequest request) {
        Gym gym = gymRepository.findById(request.getGymId())
                .orElseThrow(() -> new ResourceNotFoundException("Gym not found"));

        subscriptionRepository.findByGymIdAndStatus(request.getGymId(), "ACTIVE")
                .ifPresent(s -> {
                    throw new BadRequestException("Active subscription already exists");
                });

        GymOsSubscription subscription = GymOsSubscription.builder()
                .id(UUID.randomUUID())
                .gym(gym)
                .planType(request.getPlanType())
                .priceMonthly(request.getPriceMonthly())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(request.getDurationMonths()))
                .autoRenew(request.getAutoRenew() != null ? request.getAutoRenew() : false)
                .status("ACTIVE")
                .build();

        GymOsSubscription saved = subscriptionRepository.save(subscription);
        return mapToResponse(saved);
    }

    public SubscriptionResponse getActiveSubscription(UUID gymId) {
        GymOsSubscription subscription = subscriptionRepository.findByGymIdAndStatus(gymId, "ACTIVE")
                .orElseThrow(() -> new ResourceNotFoundException("No active subscription found"));

        return mapToResponse(subscription);
    }

    public void cancelSubscription(UUID subscriptionId) {
        GymOsSubscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        subscription.setStatus("CANCELLED");
        subscriptionRepository.save(subscription);
    }

    private SubscriptionResponse mapToResponse(GymOsSubscription subscription) {
        return SubscriptionResponse.builder()
                .id(subscription.getId())
                .gymId(subscription.getGym().getId())
                .planType(subscription.getPlanType())
                .priceMonthly(subscription.getPriceMonthly())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .status(subscription.getStatus())
                .autoRenew(subscription.getAutoRenew())
                .build();
    }
}
