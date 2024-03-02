package ru.vitrix.dto.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vitrix.dto.UserDto;
import ru.vitrix.entity.UserEntity;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserMapper {
    UserEntity toEntity(UserDto userDto);

    @Mapping(target = "avatarId", source = "avatar.id")
    UserDto toDto(UserEntity userEntity);
}
