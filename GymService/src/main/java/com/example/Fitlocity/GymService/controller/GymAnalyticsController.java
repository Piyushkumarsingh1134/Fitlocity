package com.fitlocity.gym.controller;

import com.fitlocity.gym.dto.response.AnalyticsResponse;
import com.fitlocity.gym.service.GymAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/gyms/{gymId}/analytics")
@RequiredArgsConstructor
public class GymAnalyticsController {

    private final GymAnalyticsService analyticsService;

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping
    public AnalyticsResponse getAnalytics(@PathVariable UUID gymId) {
        return analyticsService.getGymAnalytics(gymId);
    }
}
