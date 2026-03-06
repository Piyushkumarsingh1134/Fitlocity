package com.example.user_service.mapper;

import com.example.user_service.dto.request.UserRequestDTO;
import com.example.user_service.dto.response.UserResponseDTO;
import com.example.user_service.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Ignore password & sensitive fields when mapping to response
    @Mapping(target = "passwordHash", ignore = true)
    User toEntity(UserRequestDTO dto);

    UserResponseDTO toResponse(User user);

    // Use for update — only overwrite non-null fields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDTO(UserRequestDTO dto, @MappingTarget User user);
}