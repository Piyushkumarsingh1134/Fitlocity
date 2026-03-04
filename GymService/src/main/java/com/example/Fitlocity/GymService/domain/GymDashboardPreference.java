package com.fitlocity.gym.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "gym_dashboard_preferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GymDashboardPreference {

    @Id
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id", unique = true)
    private Gym gym;

    private Boolean notificationEmail;
    private Boolean notificationWhatsapp;
    private Boolean alertOnNewLead;
    private Boolean alertOnTrialBooking;
    private Boolean weeklyReport;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (notificationEmail == null) notificationEmail = true;
        if (notificationWhatsapp == null) notificationWhatsapp = false;
        if (alertOnNewLead == null) alertOnNewLead = true;
        if (alertOnTrialBooking == null) alertOnTrialBooking = true;
        if (weeklyReport == null) weeklyReport = true;
    }
}
