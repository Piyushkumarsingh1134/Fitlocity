package com.fitness.auth_service.controller;

import com.fitness.auth_service.model.Role;
import com.fitness.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/request-otp")
    public ResponseEntity<?> requestOtp(@RequestBody Map<String, String> body) {
        String phone = body.get("phone");
        String roleStr = body.getOrDefault("role", "CUSTOMER");

        Role role = Role.valueOf(roleStr.toUpperCase());

        authService.requestOtp(phone, role);
        return ResponseEntity.ok("OTP sent");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> body,
            @RequestHeader(value = "User-Agent", required = false) String deviceInfo,
            @RequestHeader(value = "X-Forwarded-For", required = false) String ipAddress) {

        return ResponseEntity.ok(
                authService.verifyOtp(
                        body.get("phone"),
                        body.get("otp"),
                        deviceInfo,
                        ipAddress));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(
                authService.refreshToken(body.get("refreshToken")));
    }
}