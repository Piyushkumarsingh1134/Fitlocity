package com.fitlocity.gym.controller;

import com.fitlocity.gym.domain.*;
import com.fitlocity.gym.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final GymOwnerRepository gymOwnerRepository;
    private final GymRepository gymRepository;
    private final GymEquipmentRepository gymEquipmentRepository;
    private final MembershipTierRepository membershipTierRepository;
    private final OfferRepository offerRepository;

    @GetMapping
    public Map<String, Object> test() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Gym Service API working");
        response.put("status", "OK");
        response.put("timestamp", LocalDateTime.now());
        response.put("service", "GymService");
        return response;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "GymService");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/echo")
    public ResponseEntity<Map<String, Object>> echo(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        response.put("received", payload);
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @PostMapping("/create-owner")
    public ResponseEntity<Map<String, Object>> createTestOwner(@RequestBody Map<String, String> request) {
        GymOwner owner = new GymOwner();
        owner.setUserId(UUID.randomUUID());
        owner.setBusinessName(request.getOrDefault("businessName", "Test Gym Business"));
        owner.setOwnerName(request.getOrDefault("ownerName", "Test Owner"));
        owner.setOwnerPhone(request.getOrDefault("ownerPhone", "9876543210"));
        owner.setOwnerEmail(request.getOrDefault("ownerEmail", "test@gym.com"));
        owner.setCommissionRate(new BigDecimal("12.5"));

        GymOwner saved = gymOwnerRepository.save(owner);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("ownerId", saved.getId());
        response.put("ownerName", saved.getOwnerName());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-gym")
    public ResponseEntity<Map<String, Object>> createTestGym(@RequestBody Map<String, String> request) {
        String ownerIdStr = request.get("ownerId");
        if (ownerIdStr == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "ownerId is required"));
        }

        UUID ownerId = UUID.fromString(ownerIdStr);
        GymOwner owner = gymOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Gym gym = new Gym();
        gym.setOwner(owner);
        gym.setName(request.getOrDefault("name", "Test Gym"));
        gym.setSlug(request.getOrDefault("slug", "test-gym-" + System.currentTimeMillis()));
        gym.setDescription(request.getOrDefault("description", "Test gym description"));
        gym.setAddress(request.getOrDefault("address", "Test Address, Chandigarh"));
        gym.setGymType(request.getOrDefault("gymType", "Strength Training"));
        gym.setContactPhone(request.getOrDefault("contactPhone", "9876543210"));
        gym.setContactEmail(request.getOrDefault("contactEmail", "gym@test.com"));
        gym.setIsVerified(false);
        gym.setRating(BigDecimal.ZERO);
        gym.setReviewCount(0);

        Gym saved = gymRepository.save(gym);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("gymId", saved.getId());
        response.put("gymName", saved.getName());
        response.put("slug", saved.getSlug());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-equipment")
    public ResponseEntity<Map<String, Object>> createEquipment(@RequestBody Map<String, String> request) {
        try {
            UUID gymId = UUID.fromString(request.get("gymId"));
            Gym gym = gymRepository.findById(gymId)
                    .orElseThrow(() -> new RuntimeException("Gym not found"));

            GymEquipment equipment = new GymEquipment();
            equipment.setGym(gym);
            equipment.setCategory(request.getOrDefault("category", "Strength"));
            equipment.setEquipmentName(request.get("equipmentName"));
            equipment.setBrand(request.get("brand"));
            equipment.setQuantity(Integer.parseInt(request.getOrDefault("quantity", "1")));
            equipment.setCondition(request.getOrDefault("condition", "Good"));
            equipment.setIsFunctional(true);

            GymEquipment saved = gymEquipmentRepository.save(equipment);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("equipmentId", saved.getId());
            response.put("equipmentName", saved.getEquipmentName());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @PostMapping("/create-membership")
    public ResponseEntity<Map<String, Object>> createMembership(@RequestBody Map<String, String> request) {
        try {
            UUID gymId = UUID.fromString(request.get("gymId"));
            Gym gym = gymRepository.findById(gymId)
                    .orElseThrow(() -> new RuntimeException("Gym not found"));

            GymMembershipTier tier = new GymMembershipTier();
            tier.setGym(gym);
            tier.setName(request.get("name"));
            tier.setDurationMonths(Integer.parseInt(request.get("durationMonths")));
            tier.setPriceMonthly(new BigDecimal(request.get("priceMonthly")));
            tier.setPriceTotal(new BigDecimal(request.get("priceTotal")));
            tier.setJoiningFee(new BigDecimal(request.getOrDefault("joiningFee", "0")));

            GymMembershipTier saved = membershipTierRepository.save(tier);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("tierId", saved.getId());
            response.put("name", saved.getName());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @PostMapping("/create-offer")
    public ResponseEntity<Map<String, Object>> createOffer(@RequestBody Map<String, String> request) {
        try {
            UUID gymId = UUID.fromString(request.get("gymId"));
            Gym gym = gymRepository.findById(gymId)
                    .orElseThrow(() -> new RuntimeException("Gym not found"));

            GymOffer offer = new GymOffer();
            offer.setGym(gym);
            offer.setTitle(request.get("title"));
            offer.setDescription(request.get("description"));
            offer.setDiscountType(request.getOrDefault("discountType", "PERCENTAGE"));
            offer.setDiscountValue(new BigDecimal(request.get("discountValue")));
            offer.setMaxRedemptions(Integer.parseInt(request.getOrDefault("maxRedemptions", "100")));

            GymOffer saved = offerRepository.save(offer);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("offerId", saved.getId());
            response.put("title", saved.getTitle());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/owners")
    public ResponseEntity<List<GymOwner>> getAllOwners() {
        return ResponseEntity.ok(gymOwnerRepository.findAll());
    }

    @GetMapping("/gyms")
    public ResponseEntity<List<Gym>> getAllGyms() {
        return ResponseEntity.ok(gymRepository.findAll());
    }

    @GetMapping("/equipment/{gymId}")
    public ResponseEntity<List<GymEquipment>> getGymEquipment(@PathVariable UUID gymId) {
        return ResponseEntity.ok(gymEquipmentRepository.findAll());
    }

    @GetMapping("/memberships/{gymId}")
    public ResponseEntity<List<GymMembershipTier>> getGymMemberships(@PathVariable UUID gymId) {
        return ResponseEntity.ok(membershipTierRepository.findAll());
    }

    @GetMapping("/offers/{gymId}")
    public ResponseEntity<List<GymOffer>> getGymOffers(@PathVariable UUID gymId) {
        return ResponseEntity.ok(offerRepository.findAll());
    }

    @DeleteMapping("/cleanup")
    public ResponseEntity<Map<String, String>> cleanup() {
        offerRepository.deleteAll();
        membershipTierRepository.deleteAll();
        gymEquipmentRepository.deleteAll();
        gymRepository.deleteAll();
        gymOwnerRepository.deleteAll();

        Map<String, String> response = new HashMap<>();
        response.put("message", "All data deleted");
        return ResponseEntity.ok(response);
    }
}
