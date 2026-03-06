package com.fitlocity.gym.service;

import com.fitlocity.gym.domain.Gym;
import com.fitlocity.gym.domain.GymMembershipTier;
import com.fitlocity.gym.dto.request.CreateMembershipTierRequest;
import com.fitlocity.gym.dto.response.MembershipTierResponse;
import com.fitlocity.gym.exception.ResourceNotFoundException;
import com.fitlocity.gym.repository.GymRepository;
import com.fitlocity.gym.repository.MembershipTierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembershipTierService {

    private final MembershipTierRepository tierRepository;
    private final GymRepository gymRepository;

    public MembershipTierResponse createTier(UUID gymId, CreateMembershipTierRequest request) {
        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new ResourceNotFoundException("Gym not found"));

        GymMembershipTier tier = GymMembershipTier.builder()
                .id(UUID.randomUUID())
                .gym(gym)
                .name(request.getName())
                .description(request.getDescription())
                .durationMonths(request.getDurationMonths())
                .priceMonthly(request.getPriceMonthly())
                .priceTotal(request.getPriceTotal())
                .joiningFee(request.getJoiningFee())
                .build();

        GymMembershipTier saved = tierRepository.save(tier);
        return mapToResponse(saved);
    }

    public List<MembershipTierResponse> getTiersByGymId(UUID gymId) {
        return tierRepository.findByGymId(gymId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteTier(UUID tierId) {
        tierRepository.deleteById(tierId);
    }

    private MembershipTierResponse mapToResponse(GymMembershipTier tier) {
        return MembershipTierResponse.builder()
                .id(tier.getId())
                .name(tier.getName())
                .durationMonths(tier.getDurationMonths())
                .priceMonthly(tier.getPriceMonthly())
                .priceTotal(tier.getPriceTotal())
                .joiningFee(tier.getJoiningFee())
                .build();
    }
}
