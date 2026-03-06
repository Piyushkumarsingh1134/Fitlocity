package com.fitlocity.gym.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class OfferResponse {
    private UUID id;
    private String title;
    private String description;
    private String discountType;
    private BigDecimal discountValue;
    private Integer maxRedemptions;
}
