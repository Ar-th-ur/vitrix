package ru.vitrix.service;

import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.dto.request.UserRequest;
import ru.vitrix.dto.response.entity.UserResponse;
import ru.vitrix.entity.UserEntity;

import java.util.UUID;

public interface UserService {
    UserResponse save(UserRequest userRequest, MultipartFile file);

    boolean existByUsername(String username);

    UserResponse update(UserEntity userEntity);

    UserEntity findById(UUID id);

    UserResponse getById(UUID id);

    UserEntity findByUsername(String username);

    UserResponse getByUsername(String username);
}
