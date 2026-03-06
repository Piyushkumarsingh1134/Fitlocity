package com.fitlocity.gym.service;

import com.fitlocity.gym.domain.Gym;
import com.fitlocity.gym.domain.GymOffer;
import com.fitlocity.gym.dto.request.CreateOfferRequest;
import com.fitlocity.gym.dto.response.OfferResponse;
import com.fitlocity.gym.exception.ResourceNotFoundException;
import com.fitlocity.gym.repository.GymRepository;
import com.fitlocity.gym.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final GymRepository gymRepository;

    public OfferResponse createOffer(UUID gymId, CreateOfferRequest request) {
        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new ResourceNotFoundException("Gym not found"));

        GymOffer offer = GymOffer.builder()
                .id(UUID.randomUUID())
                .gym(gym)
                .title(request.getTitle())
                .description(request.getDescription())
                .discountType(request.getDiscountType())
                .discountValue(request.getDiscountValue())
                .maxRedemptions(request.getMaxRedemptions())
                .currentRedemptions(0)
                .build();

        GymOffer saved = offerRepository.save(offer);
        return mapToResponse(saved);
    }

    public List<OfferResponse> getOffersByGymId(UUID gymId) {
        return offerRepository.findAll().stream()
                .filter(o -> o.getGym().getId().equals(gymId))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteOffer(UUID offerId) {
        offerRepository.deleteById(offerId);
    }

    private OfferResponse mapToResponse(GymOffer offer) {
        return OfferResponse.builder()
                .id(offer.getId())
                .title(offer.getTitle())
                .description(offer.getDescription())
                .discountType(offer.getDiscountType())
                .discountValue(offer.getDiscountValue())
                .maxRedemptions(offer.getMaxRedemptions())
                .build();
    }
}
