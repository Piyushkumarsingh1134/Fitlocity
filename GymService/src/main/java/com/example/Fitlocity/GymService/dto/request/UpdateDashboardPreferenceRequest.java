package com.fitlocity.gym.dto.request;

import lombok.Data;

@Data
public class UpdateDashboardPreferenceRequest {
    private Boolean notificationEmail;
    private Boolean notificationWhatsapp;
    private Boolean alertOnNewLead;
    private Boolean alertOnTrialBooking;
    private Boolean weeklyReport;
}
