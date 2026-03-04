package com.fitlocity.gym.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "gym_equipment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GymEquipment {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    private String category;

    @Column(nullable = false)
    private String equipmentName;

    private String brand;
    private String model;

    @Column(nullable = false)
    private Integer quantity;

    private String condition;
    private LocalDate lastMaintenanceDate;
    private LocalDate nextMaintenanceDue;
    private Boolean isFunctional;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (isFunctional == null) isFunctional = true;
    }
}
