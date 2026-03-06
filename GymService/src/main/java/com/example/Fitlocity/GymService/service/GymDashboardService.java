package com.fitlocity.gym.service;

import com.fitlocity.gym.domain.Gym;
import com.fitlocity.gym.domain.GymDashboardPreference;
import com.fitlocity.gym.dto.request.UpdateDashboardPreferenceRequest;
import com.fitlocity.gym.dto.response.DashboardPreferenceResponse;
import com.fitlocity.gym.exception.ResourceNotFoundException;
import com.fitlocity.gym.repository.GymDashboardRepository;
import com.fitlocity.gym.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GymDashboardService {

    private final GymDashboardRepository dashboardRepository;
    private final GymRepository gymRepository;

    public DashboardPreferenceResponse getPreferences(UUID gymId) {
        GymDashboardPreference pref = dashboardRepository.findByGymId(gymId)
                .orElseGet(() -> createDefaultPreferences(gymId));

        return mapToResponse(pref);
    }

    public DashboardPreferenceResponse updatePreferences(UUID gymId, UpdateDashboardPreferenceRequest request) {
        GymDashboardPreference pref = dashboardRepository.findByGymId(gymId)
                .orElseGet(() -> createDefaultPreferences(gymId));

        if (request.getNotificationEmail() != null) pref.setNotificationEmail(request.getNotificationEmail());
        if (request.getNotificationWhatsapp() != null) pref.setNotificationWhatsapp(request.getNotificationWhatsapp());
        if (request.getAlertOnNewLead() != null) pref.setAlertOnNewLead(request.getAlertOnNewLead());
        if (request.getAlertOnTrialBooking() != null) pref.setAlertOnTrialBooking(request.getAlertOnTrialBooking());
        if (request.getWeeklyReport() != null) pref.setWeeklyReport(request.getWeeklyReport());

        GymDashboardPreference saved = dashboardRepository.save(pref);
        return mapToResponse(saved);
    }

    private GymDashboardPreference createDefaultPreferences(UUID gymId) {
        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new ResourceNotFoundException("Gym not found"));

        GymDashboardPreference pref = GymDashboardPreference.builder()
                .id(UUID.randomUUID())
                .gym(gym)
                .build();

        return dashboardRepository.save(pref);
    }

    private DashboardPreferenceResponse mapToResponse(GymDashboardPreference pref) {
        return DashboardPreferenceResponse.builder()
                .id(pref.getId())
                .gymId(pref.getGym().getId())
                .notificationEmail(pref.getNotificationEmail())
                .notificationWhatsapp(pref.getNotificationWhatsapp())
                .alertOnNewLead(pref.getAlertOnNewLead())
                .alertOnTrialBooking(pref.getAlertOnTrialBooking())
                .weeklyReport(pref.getWeeklyReport())
                .build();
    }
}
