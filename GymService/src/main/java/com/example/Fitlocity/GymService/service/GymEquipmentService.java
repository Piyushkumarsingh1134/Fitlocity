package com.fitlocity.gym.service;

import com.fitlocity.gym.domain.Gym;
import com.fitlocity.gym.domain.GymEquipment;
import com.fitlocity.gym.dto.request.CreateEquipmentRequest;
import com.fitlocity.gym.dto.response.EquipmentResponse;
import com.fitlocity.gym.exception.ResourceNotFoundException;
import com.fitlocity.gym.repository.GymEquipmentRepository;
import com.fitlocity.gym.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GymEquipmentService {

    private final GymEquipmentRepository equipmentRepository;
    private final GymRepository gymRepository;

    public EquipmentResponse addEquipment(UUID gymId, CreateEquipmentRequest request) {
        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new ResourceNotFoundException("Gym not found"));

        GymEquipment equipment = GymEquipment.builder()
                .id(UUID.randomUUID())
                .gym(gym)
                .category(request.getCategory())
                .equipmentName(request.getEquipmentName())
                .brand(request.getBrand())
                .model(request.getModel())
                .quantity(request.getQuantity())
                .condition(request.getCondition())
                .isFunctional(true)
                .build();

        GymEquipment saved = equipmentRepository.save(equipment);
        return mapToResponse(saved);
    }

    public List<EquipmentResponse> getEquipmentByGymId(UUID gymId) {
        return equipmentRepository.findByGymId(gymId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteEquipment(UUID equipmentId) {
        equipmentRepository.deleteById(equipmentId);
    }

    private EquipmentResponse mapToResponse(GymEquipment equipment) {
        return EquipmentResponse.builder()
                .id(equipment.getId())
                .category(equipment.getCategory())
                .equipmentName(equipment.getEquipmentName())
                .brand(equipment.getBrand())
                .quantity(equipment.getQuantity())
                .condition(equipment.getCondition())
                .build();
    }
}
