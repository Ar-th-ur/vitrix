package ru.vitrix.service;

import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.request.UserRequest;
import ru.vitrix.entity.UserEntity;

import java.util.UUID;

public interface UserService {
    void save(UserRequest userRequest, MultipartFile file);

    boolean existByUsername(String username);

    void update(UserEntity userEntity);

    UserEntity findById(UUID id);

    UserEntity findByUsername(String username);
}
