package com.fitlocity.gym.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateGymRequest {

    @NotBlank(message = "Gym name is required")
    @Size(max = 200)
    private String name;

    @NotBlank(message = "Slug is required")
    @Size(max = 200)
    private String slug;

    private String description;

    @NotBlank(message = "Address is required")
    private String address;

    private String gymType;

    private String contactPhone;

    @Email(message = "Invalid email format")
    private String contactEmail;
}