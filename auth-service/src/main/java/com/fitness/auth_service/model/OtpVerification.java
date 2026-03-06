package com.fitness.auth_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "otp_verifications")
public class OtpVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String phone;

    private String otpHash;

    private LocalDateTime expiresAt;

    private int attemptCount = 0;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isUsed = false;

    private LocalDateTime createdAt = LocalDateTime.now();
}