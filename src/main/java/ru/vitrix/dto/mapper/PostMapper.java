package ru.vitrix.dto.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vitrix.dto.request.PostRequest;
import ru.vitrix.dto.response.entity.PostResponse;
import ru.vitrix.entity.PostEntity;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface PostMapper {
    PostEntity toEntity(PostRequest postRequest);

    @Mapping(target = "ownerId", source = "owner.id")
    PostResponse toResponse(PostEntity postEntity);
}
