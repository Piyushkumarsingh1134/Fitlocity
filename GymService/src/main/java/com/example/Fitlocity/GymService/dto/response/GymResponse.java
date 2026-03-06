package com.fitlocity.gym.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class GymResponse {

    private UUID id;
    private String name;
    private String slug;
    private String address;
    private String gymType;
    private Boolean isVerified;
    private BigDecimal rating;
}
