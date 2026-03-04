package com.fitlocity.gym.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "gym_lead_analytics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GymLeadAnalytics {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @Column(nullable = false)
    private LocalDate date;

    private Integer profileViews;
    private Integer trialBookings;
    private Integer trialConversions;
    private Integer chatQueries;
    private BigDecimal revenueFromFitlocity;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (profileViews == null) profileViews = 0;
        if (trialBookings == null) trialBookings = 0;
        if (trialConversions == null) trialConversions = 0;
        if (chatQueries == null) chatQueries = 0;
        if (revenueFromFitlocity == null) revenueFromFitlocity = BigDecimal.ZERO;
    }
}
