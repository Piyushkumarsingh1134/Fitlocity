package com.fitlocity.gym.controller;

import com.fitlocity.gym.dto.request.CreateSubscriptionRequest;
import com.fitlocity.gym.dto.response.SubscriptionResponse;
import com.fitlocity.gym.service.GymSubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class GymSubscriptionController {

    private final GymSubscriptionService subscriptionService;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public SubscriptionResponse createSubscription(@Valid @RequestBody CreateSubscriptionRequest request) {
        return subscriptionService.createSubscription(request);
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/{gymId}")
    public SubscriptionResponse getSubscription(@PathVariable UUID gymId) {
        return subscriptionService.getActiveSubscription(gymId);
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{subscriptionId}")
    public void cancelSubscription(@PathVariable UUID subscriptionId) {
        subscriptionService.cancelSubscription(subscriptionId);
    }
}
