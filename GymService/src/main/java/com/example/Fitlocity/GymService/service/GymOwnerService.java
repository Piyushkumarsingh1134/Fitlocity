package com.fitlocity.gym.service;

import com.fitlocity.gym.domain.GymOwner;
import com.fitlocity.gym.exception.BadRequestException;
import com.fitlocity.gym.exception.ResourceNotFoundException;
import com.fitlocity.gym.repository.GymOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GymOwnerService {

    private final GymOwnerRepository gymOwnerRepository;

    public GymOwner createOwner(GymOwner owner) {

        if (gymOwnerRepository.existsByUserId(owner.getUserId())) {
            throw new BadRequestException("Owner already exists for this user");
        }

        owner.setId(UUID.randomUUID());
        owner.setCreatedAt(LocalDateTime.now());
        owner.setUpdatedAt(LocalDateTime.now());

        return gymOwnerRepository.save(owner);
    }

    public GymOwner getOwner(UUID id) {

        return gymOwnerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
    }
}