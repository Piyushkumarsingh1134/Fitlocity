package com.example.user_service.model;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(unique = true, nullable = false, length = 255)
    private String email;

    @Column(unique = true, length = 20)
    private String phone;

    @Column(name = "password_hash", length = 255)
    private String passwordHash;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "profile_photo_url", columnDefinition = "TEXT")
    private String profilePhotoUrl;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(length = 20)
    private String gender;

    @Column(name = "city_id")
    private UUID cityId;

    @Column(name = "home_location", columnDefinition = "geography(Point,4326)")
    private String homeLocation;

    @Column(name = "work_location", columnDefinition = "geography(Point,4326)")
    private String workLocation;

    @Column(name = "fitness_goals", columnDefinition = "jsonb")
    private String fitnessGoals;

    @Column(name = "experience_level", length = 20)
    private String experienceLevel;

    @Column(name = "dietary_preferences", columnDefinition = "jsonb")
    private String dietaryPreferences;

    @Column(name = "health_conditions", columnDefinition = "jsonb")
    private String healthConditions;

    @Column(name = "is_verified", nullable = false)
    @Builder.Default
    private Boolean isVerified = false;

    @Column(name = "is_premium", nullable = false)
    @Builder.Default
    private Boolean isPremium = false;

    @Column(name = "premium_expiry")
    private LocalDateTime premiumExpiry;

    @Column(name = "credibility_score", precision = 3, scale = 2)
    private BigDecimal credibilityScore;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_active")
    private LocalDateTime lastActive;

    @Column(name = "referral_code", unique = true, length = 20)
    private String referralCode;

    @ManyToOne
    @JoinColumn(name = "referred_by")
    private User referredBy;

    
}