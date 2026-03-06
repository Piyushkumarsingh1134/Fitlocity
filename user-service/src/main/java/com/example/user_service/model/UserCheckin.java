package com.example.user_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserCheckin {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="user_id" , nullable = false)
    private  User user;

    @Column(name="gym_id" , nullable = false)
    private  UUID gymId;

    @ManyToOne
    @JoinColumn(name = "membership_id")
    private UserMembership userMembership;

    @Column(name = "checkin_time")
    private LocalDateTime checkinTime;

    @Column(name = "checkout_time")
    private LocalDateTime checkoutTime;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "crowd_density_observed")
    private Integer crowdDensityObserved;

    @Column(name = "equipment_wait_time_minutes")
    private Integer equipmentWaitTimeMinutes;
}