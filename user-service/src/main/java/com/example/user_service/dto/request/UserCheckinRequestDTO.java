package com.example.user_service.dto.request;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCheckinRequestDTO {

    private UUID userId;
    private UUID gymId;
    private UUID membershipId;
    private LocalDateTime checkinTime;
    private Integer crowdDensityObserved;
    private Integer equipmentWaitTimeMinutes;
}