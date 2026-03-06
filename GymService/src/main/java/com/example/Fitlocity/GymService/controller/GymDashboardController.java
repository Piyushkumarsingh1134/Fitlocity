package com.fitlocity.gym.controller;

import com.fitlocity.gym.dto.request.UpdateDashboardPreferenceRequest;
import com.fitlocity.gym.dto.response.DashboardPreferenceResponse;
import com.fitlocity.gym.service.GymDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/dashboard/preferences")
@RequiredArgsConstructor
public class GymDashboardController {

    private final GymDashboardService dashboardService;

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/{gymId}")
    public DashboardPreferenceResponse getPreferences(@PathVariable UUID gymId) {
        return dashboardService.getPreferences(gymId);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/{gymId}")
    public DashboardPreferenceResponse updatePreferences(@PathVariable UUID gymId,
                                                         @RequestBody UpdateDashboardPreferenceRequest request) {
        return dashboardService.updatePreferences(gymId, request);
    }
}
