package com.fitlocity.gym.controller;

import com.fitlocity.gym.dto.request.CreateGymRequest;
import com.fitlocity.gym.dto.request.UpdateGymRequest;
import com.fitlocity.gym.dto.response.GymResponse;
import com.fitlocity.gym.service.GymService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/gyms")
@RequiredArgsConstructor
public class GymController {

    private final GymService gymService;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public GymResponse createGym(@Valid @RequestBody CreateGymRequest request) {

        UUID userId = (UUID) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return gymService.createGym(userId, request);
    }

    @GetMapping
    public Page<GymResponse> getAllGyms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy) {

        return gymService.getAllGyms(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public GymResponse getGymById(@PathVariable UUID id) {

        return gymService.getGymById(id);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/{id}")
    public GymResponse updateGym(@PathVariable UUID id,
                                  @RequestBody UpdateGymRequest request) {
        return gymService.updateGym(id, request);
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGym(@PathVariable UUID id) {
        gymService.deleteGym(id);
    }
}