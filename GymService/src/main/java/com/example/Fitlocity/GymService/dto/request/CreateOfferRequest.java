package com.fitlocity.gym.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOfferRequest {
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    @NotBlank(message = "Discount type is required")
    @Pattern(regexp = "PERCENTAGE|FIXED", message = "Discount type must be PERCENTAGE or FIXED")
    private String discountType;
    
    @NotNull(message = "Discount value is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount value must be greater than 0")
    private BigDecimal discountValue;
    
    @Positive(message = "Max redemptions must be positive")
    private Integer maxRedemptions;
}
