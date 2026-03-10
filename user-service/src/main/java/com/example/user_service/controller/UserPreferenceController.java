package com.example.user_service.controller;

import com.example.user_service.dto.request.UserPreferenceRequestDTO;
import com.example.user_service.dto.response.UserResponsePreferenceDTO;
import com.example.user_service.service.UserPreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/users/{userId}/preferences")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;

    // POST /api/v1/users/{userId}/preferences
    @PostMapping
    public ResponseEntity<UserResponsePreferenceDTO> createPreference(
            @PathVariable UUID userId,
            @Valid @RequestBody UserPreferenceRequestDTO requestDto
    ) {
        System.out.println("POST /api/v1/users/{}/preferences" + userId);
    
        requestDto.setUserId(userId);

        UserResponsePreferenceDTO response = userPreferenceService.createPreference(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/v1/users/{userId}/preferences
    @GetMapping
    public ResponseEntity<UserResponsePreferenceDTO> getPreference(
            @PathVariable UUID userId
    ) {
        log.info("GET /api/v1/users/{}/preferences", userId);
        UserResponsePreferenceDTO response = userPreferenceService.getPreferenceByUserId(userId);
        return ResponseEntity.ok(response);
    }

    // PUT /api/v1/users/{userId}/preferences
    @PutMapping
    public ResponseEntity<UserResponsePreferenceDTO> updatePreference(
            @PathVariable UUID userId,
            @Valid @RequestBody UserPreferenceRequestDTO requestDto
    ) {
        log.info("PUT /api/v1/users/{}/preferences", userId);
        requestDto.setUserId(userId);
        UserResponsePreferenceDTO response = userPreferenceService.updatePreference(userId, requestDto);
        return ResponseEntity.ok(response);
    }

    // PATCH /api/v1/users/{userId}/preferences
    @PatchMapping
    public ResponseEntity<UserResponsePreferenceDTO> patchPreference(
            @PathVariable UUID userId,
            @RequestBody UserPreferenceRequestDTO requestDto
    ) {
        log.info("PATCH /api/v1/users/{}/preferences", userId);
        UserResponsePreferenceDTO response = userPreferenceService.updatePreference(userId, requestDto);
        return ResponseEntity.ok(response);
    }

    // DELETE /api/v1/users/{userId}/preferences
    @DeleteMapping
    public ResponseEntity<Void> deletePreference(
            @PathVariable UUID userId
    ) {
        log.info("DELETE /api/v1/users/{}/preferences", userId);
        userPreferenceService.deletePreference(userId);
        return ResponseEntity.noContent().build();
    }

    // HEAD /api/v1/users/{userId}/preferences
    @RequestMapping(method = RequestMethod.HEAD)
    public ResponseEntity<Void> preferenceExists(
            @PathVariable UUID userId
    ) {
        log.info("HEAD /api/v1/users/{}/preferences", userId);
        boolean exists = userPreferenceService.preferenceExists(userId);
        return exists
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}