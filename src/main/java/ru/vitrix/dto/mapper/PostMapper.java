package ru.vitrix.dto.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vitrix.dto.PostDto;
import ru.vitrix.entity.PostEntity;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface PostMapper {
    PostEntity toEntity(PostDto postDto);

    @Mapping(target = "ownerId", source = "owner.id")
    PostDto toDto(PostEntity postEntity);
}
