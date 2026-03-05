package com.fitlocity.gym.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "gym_offers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GymOffer {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_tier_id")
    private GymMembershipTier membershipTier;

    @Column(nullable = false)
    private String title;

    private String description;
    private String discountType;
    private BigDecimal discountValue;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private Integer maxRedemptions;
    private Integer currentRedemptions;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (currentRedemptions == null) currentRedemptions = 0;
    }
}
