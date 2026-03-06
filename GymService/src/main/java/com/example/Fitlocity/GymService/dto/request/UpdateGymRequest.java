package com.fitlocity.gym.dto.request;

import lombok.Data;

@Data
public class UpdateGymRequest {
    private String name;
    private String description;
    private String address;
    private String gymType;
    private String contactPhone;
    private String contactEmail;
    private Integer totalSqft;
    private Integer floorsCount;
}
