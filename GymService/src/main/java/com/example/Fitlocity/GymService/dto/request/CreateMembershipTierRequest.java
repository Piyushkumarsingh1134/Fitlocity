package com.fitlocity.gym.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateMembershipTierRequest {
    private String name;
    private String description;
    private Integer durationMonths;
    private BigDecimal priceMonthly;
    private BigDecimal priceTotal;
    private BigDecimal joiningFee;
}
