package com.fitlocity.gym.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateGymRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Slug is required")
    private String slug;

    private String description;
    private String address;
    private String gymType;
    private String contactPhone;
    private String contactEmail;
}
