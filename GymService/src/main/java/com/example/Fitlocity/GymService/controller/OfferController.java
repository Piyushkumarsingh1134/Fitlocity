package com.fitlocity.gym.controller;

import com.fitlocity.gym.dto.request.CreateOfferRequest;
import com.fitlocity.gym.dto.response.OfferResponse;
import com.fitlocity.gym.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gyms/{gymId}/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public OfferResponse createOffer(@PathVariable UUID gymId,
                                     @RequestBody CreateOfferRequest request) {
        return offerService.createOffer(gymId, request);
    }

    @GetMapping
    public List<OfferResponse> getOffers(@PathVariable UUID gymId) {
        return offerService.getOffersByGymId(gymId);
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{offerId}")
    public void deleteOffer(@PathVariable UUID offerId) {
        offerService.deleteOffer(offerId);
    }
}
