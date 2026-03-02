package com.fitlocity.gym.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "gym_owners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GymOwner {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    private String businessName;
    private String ownerName;
    private String ownerPhone;
    private String ownerEmail;

    private String gstNumber;
    private String panNumber;
    private String businessRegistrationNumber;

    @Column(columnDefinition = "jsonb")
    private String bankAccountDetails;

    private BigDecimal commissionRate;

    private LocalDate contractSignedDate;
    private LocalDate contractExpiryDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}