package com.fitlocity.gym.controller;

import com.fitlocity.gym.domain.GymOwner;
import com.fitlocity.gym.service.GymOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class GymOwnerController {

    private final GymOwnerService gymOwnerService;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public GymOwner createOwner(@RequestBody GymOwner owner) {
        return gymOwnerService.createOwner(owner);
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/{id}")
    public GymOwner getOwner(@PathVariable UUID id) {
        return gymOwnerService.getOwner(id);
    }
}