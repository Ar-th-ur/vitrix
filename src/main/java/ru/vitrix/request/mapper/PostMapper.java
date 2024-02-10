package ru.vitrix.request.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.vitrix.entity.PostEntity;
import ru.vitrix.request.PostRequest;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface PostMapper {
    PostEntity toEntity(PostRequest postRequest);
}
