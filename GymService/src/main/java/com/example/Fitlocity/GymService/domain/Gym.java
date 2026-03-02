package com.fitlocity.gym.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "gyms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gym {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private GymOwner owner;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    private String description;

    private UUID neighborhoodId;

    private String address;

    private String landmark;
    private String pincode;

    private String contactPhone;
    private String contactEmail;

    private String gymType;
    private String chainName;

    private Boolean isVerified;
    private String verificationBadgeType;

    private Integer totalSqft;
    private Integer floorsCount;

    @Column(columnDefinition = "jsonb")
    private String operatingHours;

    @Column(columnDefinition = "jsonb")
    private String peakHours;

    private Integer crowdDensityCurrent;

    @Column(columnDefinition = "jsonb")
    private String amenities;

    @Column(columnDefinition = "text[]")
    private String[] images;

    private String virtualTourUrl;

    private BigDecimal rating;
    private Integer reviewCount;
    private BigDecimal credibilityScore;

    private Boolean isFeatured;
    private String listingTier;
    private String subscriptionStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}