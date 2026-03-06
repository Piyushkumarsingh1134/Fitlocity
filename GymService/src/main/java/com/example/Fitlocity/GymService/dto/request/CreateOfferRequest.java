package com.fitlocity.gym.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOfferRequest {
    private String title;
    private String description;
    private String discountType;
    private BigDecimal discountValue;
    private Integer maxRedemptions;
}
