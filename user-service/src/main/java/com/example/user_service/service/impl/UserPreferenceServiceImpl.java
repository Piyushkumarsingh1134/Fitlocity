package com.example.user_service.service.impl;

import com.example.user_service.dto.request.UserPreferenceRequestDTO;
import com.example.user_service.dto.response.UserResponsePreferenceDTO;
import com.example.user_service.exception.DuplicateResourceException;
import com.example.user_service.exception.ResourceNotFoundException;
import com.example.user_service.mapper.UserPreferenceMapper;
import com.example.user_service.model.UserPreference;
import com.example.user_service.repository.UserPreferenceRepository;
import com.example.user_service.service.UserPreferenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserPreferenceServiceImpl implements UserPreferenceService {

    private final UserPreferenceRepository userPreferenceRepository;
    private final UserPreferenceMapper userPreferenceMapper;

    @Override
    @Transactional
    public UserResponsePreferenceDTO createPreference(UserPreferenceRequestDTO requestDto) {
        log.info("Creating preference for userId: {}", requestDto.getUserId());

        if (userPreferenceRepository.existsByUserId(requestDto.getUserId())) {
            throw new DuplicateResourceException(
                    "Preference already exists for userId: " + requestDto.getUserId()
            );
        }

        UserPreference entity = userPreferenceMapper.toEntity(requestDto);
        UserPreference saved = userPreferenceRepository.save(entity);

        log.info("Preference created with id: {}", saved.getId());
        return userPreferenceMapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponsePreferenceDTO getPreferenceByUserId(UUID userId) {
        log.info("Fetching preference for userId: {}", userId);

        UserPreference entity = userPreferenceRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Preference not found for userId: " + userId
                ));

        return userPreferenceMapper.toResponseDto(entity);
    }

    @Override
    @Transactional
    public UserResponsePreferenceDTO updatePreference(UUID userId, UserPreferenceRequestDTO requestDto) {
        log.info("Updating preference for userId: {}", userId);

        UserPreference entity = userPreferenceRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Preference not found for userId: " + userId
                ));

        userPreferenceMapper.updateEntityFromDto(requestDto, entity);
        UserPreference updated = userPreferenceRepository.save(entity);

        log.info("Preference updated for userId: {}", userId);
        return userPreferenceMapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public void deletePreference(UUID userId) {
        log.info("Deleting preference for userId: {}", userId);

        if (!userPreferenceRepository.existsByUserId(userId)) {
            throw new ResourceNotFoundException(
                    "Preference not found for userId: " + userId
            );
        }

        userPreferenceRepository.deleteByUserId(userId);
        log.info("Preference deleted for userId: {}", userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean preferenceExists(UUID userId) {
        return userPreferenceRepository.existsByUserId(userId);
    }
}