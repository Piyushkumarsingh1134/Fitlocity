package com.fitlocity.gym.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class SubscriptionResponse {
    private UUID id;
    private UUID gymId;
    private String planType;
    private BigDecimal priceMonthly;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Boolean autoRenew;
}
