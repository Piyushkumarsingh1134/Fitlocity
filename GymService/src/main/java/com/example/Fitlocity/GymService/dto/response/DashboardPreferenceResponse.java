package com.fitlocity.gym.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DashboardPreferenceResponse {
    private UUID id;
    private UUID gymId;
    private Boolean notificationEmail;
    private Boolean notificationWhatsapp;
    private Boolean alertOnNewLead;
    private Boolean alertOnTrialBooking;
    private Boolean weeklyReport;
}
