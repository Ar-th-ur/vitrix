package ru.vitrix.dto.mapper;

import org.mapstruct.Builder;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vitrix.dto.UserDto;
import ru.vitrix.entity.UserEntity;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        uses = PostMapper.class)
public interface UserMapper {
    @Mapping(target = "avatarId", source = "avatar.id")
    UserDto toDto(UserEntity userEntity);

    @InheritInverseConfiguration
    UserEntity toEntity(UserDto userDto);
}
