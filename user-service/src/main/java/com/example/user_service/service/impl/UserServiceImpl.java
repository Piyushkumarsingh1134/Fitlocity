package com.example.user_service.service.impl;

import com.example.user_service.dto.request.UserRequestDTO;
import com.example.user_service.dto.response.UserResponseDTO;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + dto.getEmail());
        }
        if (dto.getPhone() != null && userRepository.existsByPhone(dto.getPhone())) {
            throw new IllegalArgumentException("Phone already in use: " + dto.getPhone());
        }

        User user = userMapper.toEntity(dto);

        // Hash password if provided
        if (dto.getPassword() != null) {
            user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }

        // Resolve referredBy
        if (dto.getReferredById() != null) {
            User referrer = userRepository.findById(dto.getReferredById())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Referrer not found: " + dto.getReferredById()));
            user.setReferredBy(referrer);
        }

        User saved = userRepository.save(user);
        return userMapper.toResponseDTO(saved);
    }


    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(UUID id) {
        return userMapper.toResponseDTO(findUserOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
        return userMapper.toResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserByPhone(String phone) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("User not found with phone: " + phone));
        return userMapper.toResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getUsersByCity(UUID cityId) {
        return userRepository.findByCityId(cityId)
                .stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getUsersByExperienceLevelAndCity(String level, UUID cityId) {
        return userRepository.findByExperienceLevelAndCity(level, cityId)
                .stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getVerifiedPremiumUsers() {
        return userRepository.findAllVerifiedPremiumUsers()
                .stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getRecentlyActiveUsers() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        return userRepository.findRecentlyActiveUsers(sevenDaysAgo)
                .stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getUsersReferredBy(UUID referredById) {
        User referrer = userRepository.findById(referredById)
                .orElseThrow(() -> new EntityNotFoundException("Referrer not found: " + referredById));

        return userRepository.findByReferredBy(referrer)
                .stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------------------

    @Override
    @Transactional
    public UserResponseDTO updateUser(UUID id, UserRequestDTO dto) {
        User user = findUserOrThrow(id);

        // Check email uniqueness if changing
        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new IllegalArgumentException("Email already in use: " + dto.getEmail());
            }
        }

        // Check phone uniqueness if changing
        if (dto.getPhone() != null && !dto.getPhone().equals(user.getPhone())) {
            if (userRepository.existsByPhone(dto.getPhone())) {
                throw new IllegalArgumentException("Phone already in use: " + dto.getPhone());
            }
        }

        userMapper.updateEntityFromDTO(dto, user);

        // Re-hash password if provided
        if (dto.getPassword() != null) {
            user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }

        // Update referredBy if provided
        if (dto.getReferredById() != null) {
            User referrer = userRepository.findById(dto.getReferredById())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Referrer not found: " + dto.getReferredById()));
            user.setReferredBy(referrer);
        }

        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDTO verifyUser(UUID id) {
        User user = findUserOrThrow(id);
        user.setIsVerified(true);
        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDTO upgradeToPremium(UUID id, int durationDays) {
        User user = findUserOrThrow(id);
        user.setIsPremium(true);
        // Extend from current expiry if still active, otherwise from now
        LocalDateTime base = (user.getPremiumExpiry() != null && user.getPremiumExpiry().isAfter(LocalDateTime.now()))
                ? user.getPremiumExpiry()
                : LocalDateTime.now();
        user.setPremiumExpiry(base.plusDays(durationDays));
        return userMapper.toResponseDTO(userRepository.save(user));
    }

    // -------------------------------------------------------------------------
    // DELETE


    @Override
    @Transactional
    public void deleteUser(UUID id) {
        User user = findUserOrThrow(id);
        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    private User findUserOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }
}