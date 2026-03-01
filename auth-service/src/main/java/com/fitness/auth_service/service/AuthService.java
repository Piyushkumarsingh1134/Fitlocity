package com.fitness.auth_service.service;



import com.fitness.auth_service.model.*;
import com.fitness.auth_service.repository.*;
import com.fitness.auth_service.security.JwtUtil;
import com.fitness.auth_service.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final SessionRepository sessionRepository;
    private final JwtUtil jwtUtil;
    private final TokenUtil tokenUtil;

    // 1️⃣ Request OTP
    public void requestOtp(String phone) {

        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        String hash = BCrypt.hashpw(otp, BCrypt.gensalt());

        OtpVerification record = new OtpVerification();
        record.setPhone(phone);
        record.setOtpHash(hash);
        record.setExpiresAt(LocalDateTime.now().plusMinutes(5));

        otpRepository.save(record);

        System.out.println("OTP: " + otp); // replace with WhatsApp later
    }

    // 2️⃣ Verify OTP and create session
    public Map<String, String> verifyOtp(String phone, String otp, String deviceInfo, String ipAddress) {

        OtpVerification record = otpRepository
                .findTopByPhoneAndIsUsedFalseOrderByCreatedAtDesc(phone)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (record.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new RuntimeException("OTP expired");

        if (!BCrypt.checkpw(otp, record.getOtpHash()))
            throw new RuntimeException("Invalid OTP");

        record.setUsed(true);
        otpRepository.save(record);

        // find or create user
        User user = userRepository.findByPhone(phone)
                .orElseGet(() -> {
                    User u = new User();
                    u.setPhone(phone);
                    return userRepository.save(u);
                });

        // Generate tokens
        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getRole().name());
        String refreshToken = tokenUtil.generateRefreshToken();

        // Create session
        Session session = new Session();
        session.setUser(user);
        session.setRefreshToken(refreshToken);
        session.setDeviceInfo(deviceInfo);
        session.setIpAddress(ipAddress);
        session.setExpiresAt(LocalDateTime.now().plusDays(7));

        sessionRepository.save(session);

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    // 3️⃣ Refresh Token Endpoint Logic
    public Map<String, String> refreshToken(String refreshToken) {

        Session session = sessionRepository
                .findAll()
                .stream()
                .filter(s -> s.getRefreshToken().equals(refreshToken))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (session.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Refresh token expired");

        User user = session.getUser();

        String newAccessToken = jwtUtil.generateAccessToken(user.getId(), user.getRole().name());

        return Map.of("accessToken", newAccessToken);
    }
}
