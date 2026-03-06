package com.fitlocity.gym.service;

import com.fitlocity.gym.domain.Gym;
import com.fitlocity.gym.domain.GymOwner;
import com.fitlocity.gym.dto.request.CreateGymRequest;
import com.fitlocity.gym.dto.response.GymResponse;
import com.fitlocity.gym.exception.BadRequestException;
import com.fitlocity.gym.exception.ResourceNotFoundException;
import com.fitlocity.gym.repository.GymOwnerRepository;
import com.fitlocity.gym.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;
    private final GymOwnerRepository gymOwnerRepository;

    @Transactional
    public GymResponse createGym(UUID userId, CreateGymRequest request) {

        // Auto-create owner if not exists
        GymOwner owner = gymOwnerRepository.findByUserId(userId)
                .orElseGet(() -> {
                    GymOwner newOwner = new GymOwner();
                    newOwner.setId(UUID.randomUUID());
                    newOwner.setUserId(userId);
                    newOwner.setOwnerPhone(request.getContactPhone());
                    newOwner.setOwnerEmail(request.getContactEmail());
                    newOwner.setCreatedAt(LocalDateTime.now());
                    newOwner.setUpdatedAt(LocalDateTime.now());
                    return gymOwnerRepository.save(newOwner);
                });

        if (gymRepository.existsBySlug(request.getSlug())) {
            throw new BadRequestException("Slug already exists");
        }

        Gym gym = Gym.builder()
                .id(UUID.randomUUID())
                .owner(owner)
                .name(request.getName())
                .slug(request.getSlug())
                .description(request.getDescription())
                .address(request.getAddress())
                .gymType(request.getGymType())
                .contactPhone(request.getContactPhone())
                .contactEmail(request.getContactEmail())
                .isVerified(false)
                .reviewCount(0)
                .rating(null)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Gym saved = gymRepository.save(gym);

        return mapToResponse(saved);
    }

    public Page<GymResponse> getAllGyms(int page, int size, String sortBy) {

        List<String> allowedSortFields = List.of("createdAt", "rating", "name");

        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "createdAt";
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, sortBy)
        );

        Page<Gym> gymPage = gymRepository.findAll(pageable);

        return gymPage.map(this::mapToResponse);
    }

    public GymResponse getGymById(UUID id) {

        Gym gym = gymRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gym not found"));

        return mapToResponse(gym);
    }

    private GymResponse mapToResponse(Gym gym) {

        return GymResponse.builder()
                .id(gym.getId())
                .name(gym.getName())
                .slug(gym.getSlug())
                .address(gym.getAddress())
                .gymType(gym.getGymType())
                .isVerified(gym.getIsVerified())
                .rating(gym.getRating())
                .build();
    }
}