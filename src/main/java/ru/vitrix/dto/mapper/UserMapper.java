package ru.vitrix.dto.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import ru.vitrix.dto.request.UserRequest;
import ru.vitrix.dto.response.entity.UserResponse;
import ru.vitrix.entity.UserEntity;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserMapper {
    UserEntity toEntity(UserRequest userRequest);

    UserResponse toResponse(UserEntity userEntity);
}
