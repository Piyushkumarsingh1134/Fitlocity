package com.fitlocity.gym.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class MembershipTierResponse {
    private UUID id;
    private String name;
    private Integer durationMonths;
    private BigDecimal priceMonthly;
    private BigDecimal priceTotal;
    private BigDecimal joiningFee;
}
