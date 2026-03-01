package com.fitness.auth_service.util;



import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenUtil {

    public String generateRefreshToken() {
        return UUID.randomUUID().toString() + UUID.randomUUID();
    }
}
