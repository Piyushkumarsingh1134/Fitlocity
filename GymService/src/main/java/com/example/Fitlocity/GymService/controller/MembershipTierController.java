package com.fitlocity.gym.controller;

import com.fitlocity.gym.dto.request.CreateMembershipTierRequest;
import com.fitlocity.gym.dto.response.MembershipTierResponse;
import com.fitlocity.gym.service.MembershipTierService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gyms/{gymId}/membership-tiers")
@RequiredArgsConstructor
public class MembershipTierController {

    private final MembershipTierService membershipTierService;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public MembershipTierResponse createTier(@PathVariable UUID gymId,
                                             @RequestBody CreateMembershipTierRequest request) {
        return membershipTierService.createTier(gymId, request);
    }

    @GetMapping
    public List<MembershipTierResponse> getTiers(@PathVariable UUID gymId) {
        return membershipTierService.getTiersByGymId(gymId);
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{tierId}")
    public void deleteTier(@PathVariable UUID tierId) {
        membershipTierService.deleteTier(tierId);
    }
}
