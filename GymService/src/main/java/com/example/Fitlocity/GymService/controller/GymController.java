package com.fitlocity.gym.controller;

import com.fitlocity.gym.dto.request.CreateGymRequest;
import com.fitlocity.gym.dto.response.GymResponse;
import com.fitlocity.gym.service.GymService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/gyms")
@RequiredArgsConstructor
public class GymController {

    private final GymService gymService;

    // -------- CREATE GYM --------

    @PostMapping("/{ownerId}")
    public GymResponse createGym(@PathVariable UUID ownerId,
                                 @Valid @RequestBody CreateGymRequest request) {

        return gymService.createGym(ownerId, request);
    }

    // -------- GET ALL GYMS (PAGINATED) --------

    @GetMapping
    public Page<GymResponse> getAllGyms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy) {

        return gymService.getAllGyms(page, size, sortBy);
    }

    // -------- GET GYM BY ID --------

    @GetMapping("/{id}")
    public GymResponse getGym(@PathVariable UUID id) {

        return gymService.getGymById(id);
    }
}