package com.fitlocity.gym.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AnalyticsResponse {
    private UUID gymId;
    private Integer totalProfileViews;
    private Integer totalTrialBookings;
    private Integer totalConversions;
    private Double conversionRate;
}
