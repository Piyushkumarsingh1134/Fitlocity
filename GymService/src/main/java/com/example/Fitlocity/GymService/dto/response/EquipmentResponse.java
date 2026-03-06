package com.fitlocity.gym.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EquipmentResponse {
    private UUID id;
    private String category;
    private String equipmentName;
    private String brand;
    private Integer quantity;
    private String condition;
}
