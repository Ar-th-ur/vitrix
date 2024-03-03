package ru.vitrix.dto.mapper;

import org.mapstruct.*;
import ru.vitrix.dto.PostDto;
import ru.vitrix.entity.PostEntity;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class PostMapper {
    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "imageId", source = "image.id")
    public abstract PostDto toDto(PostEntity postEntity);

    @InheritInverseConfiguration
    public abstract PostEntity toEntity(PostDto postDto);

    @AfterMapping
    protected void setInDay(PostEntity entity, @MappingTarget PostDto dto) {
        var now = LocalDateTime.now();
        var createdAt = entity.getCreatedAt();
        var oneYear = now.getYear() == createdAt.getYear();
        var oneDay = now.getDayOfYear() == createdAt.getDayOfYear();
        dto.setInDay(oneYear && oneDay);
    }
}
