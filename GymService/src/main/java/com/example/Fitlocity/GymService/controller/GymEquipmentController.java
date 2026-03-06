package com.fitlocity.gym.controller;

import com.fitlocity.gym.dto.request.CreateEquipmentRequest;
import com.fitlocity.gym.dto.response.EquipmentResponse;
import com.fitlocity.gym.service.GymEquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gyms/{gymId}/equipment")
@RequiredArgsConstructor
public class GymEquipmentController {

    private final GymEquipmentService equipmentService;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public EquipmentResponse addEquipment(@PathVariable UUID gymId,
                                          @Valid @RequestBody CreateEquipmentRequest request) {
        return equipmentService.addEquipment(gymId, request);
    }

    @GetMapping
    public List<EquipmentResponse> getEquipment(@PathVariable UUID gymId) {
        return equipmentService.getEquipmentByGymId(gymId);
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{equipmentId}")
    public void deleteEquipment(@PathVariable UUID equipmentId) {
        equipmentService.deleteEquipment(equipmentId);
    }
}
