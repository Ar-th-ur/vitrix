package ru.vitrix.request.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.vitrix.request.UserRequest;
import ru.vitrix.entity.UserEntity;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserMapper {
    UserEntity toEntity(UserRequest userRequest);
}
