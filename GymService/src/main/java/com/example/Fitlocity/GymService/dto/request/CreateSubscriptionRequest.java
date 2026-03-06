package com.fitlocity.gym.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateSubscriptionRequest {
    @NotNull(message = "Gym ID is required")
    private UUID gymId;

    @NotBlank(message = "Plan type is required")
    @Pattern(regexp = "BASIC|PRO|ENTERPRISE", message = "Plan type must be BASIC, PRO, or ENTERPRISE")
    private String planType;

    @NotNull(message = "Monthly price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal priceMonthly;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    private Integer durationMonths;

    private Boolean autoRenew;
}
