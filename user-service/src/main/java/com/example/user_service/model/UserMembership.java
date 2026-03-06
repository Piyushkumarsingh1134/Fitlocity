package com.example.user_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "user_memberships",
        indexes = {
                @Index(name = "idx_membership_user", columnList = "user_id"),
                @Index(name = "idx_membership_gym", columnList = "gym_id"),
                @Index(name = "idx_membership_active", columnList = "is_active"),
                @Index(name = "idx_membership_end_date", columnList = "end_date")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMembership {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "gym_id", nullable = false)
    private UUID gymId;

    @Column(name = "membership_tier_id" , nullable = false)
    private UUID membershipTierId;

    @Column(name = "offer_id")
    private UUID offerId;

    @Column(name = "source", length = 30)
    private String source;

    @Column(name = "trial_booking_id")
    private UUID trialBookingId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "auto_renew", nullable = false)
    @Builder.Default
    private Boolean autoRenew = false;

    @Column(name = "payment_amount", precision = 10, scale = 2)
    private BigDecimal paymentAmount;

    @Column(name = "commission_to_fitlocity", precision = 10, scale = 2)
    private BigDecimal commissionToFitlocity;

    @Column(name = "commission_rate_applied", precision = 5, scale = 2)
    private BigDecimal commissionRateApplied;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    private String cancellationReason;
}