package com.fitlocity.gym.dto.request;

import lombok.Data;

@Data
public class CreateEquipmentRequest {
    private String category;
    private String equipmentName;
    private String brand;
    private String model;
    private Integer quantity;
    private String condition;
}
