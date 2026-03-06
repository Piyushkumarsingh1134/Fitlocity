package com.fitlocity.gym.service;

import com.fitlocity.gym.domain.Gym;
import com.fitlocity.gym.domain.GymLeadAnalytics;
import com.fitlocity.gym.dto.response.AnalyticsResponse;
import com.fitlocity.gym.exception.ResourceNotFoundException;
import com.fitlocity.gym.repository.GymAnalyticsRepository;
import com.fitlocity.gym.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GymAnalyticsService {

    private final GymAnalyticsRepository analyticsRepository;
    private final GymRepository gymRepository;

    public AnalyticsResponse getGymAnalytics(UUID gymId) {
        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new ResourceNotFoundException("Gym not found"));

        List<GymLeadAnalytics> analytics = analyticsRepository.findByGymIdOrderByDateDesc(gymId);

        int totalViews = analytics.stream().mapToInt(GymLeadAnalytics::getProfileViews).sum();
        int totalBookings = analytics.stream().mapToInt(GymLeadAnalytics::getTrialBookings).sum();
        int totalConversions = analytics.stream().mapToInt(GymLeadAnalytics::getTrialConversions).sum();

        return AnalyticsResponse.builder()
                .gymId(gymId)
                .totalProfileViews(totalViews)
                .totalTrialBookings(totalBookings)
                .totalConversions(totalConversions)
                .conversionRate(totalBookings > 0 ? (double) totalConversions / totalBookings * 100 : 0.0)
                .build();
    }

    public void incrementProfileView(UUID gymId) {
        LocalDate today = LocalDate.now();
        GymLeadAnalytics analytics = analyticsRepository.findByGymIdAndDate(gymId, today)
                .orElseGet(() -> createNewAnalytics(gymId, today));

        analytics.setProfileViews(analytics.getProfileViews() + 1);
        analyticsRepository.save(analytics);
    }

    private GymLeadAnalytics createNewAnalytics(UUID gymId, LocalDate date) {
        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new ResourceNotFoundException("Gym not found"));

        return GymLeadAnalytics.builder()
                .id(UUID.randomUUID())
                .gym(gym)
                .date(date)
                .build();
    }
}
