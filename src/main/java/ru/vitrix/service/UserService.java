package ru.vitrix.service;

import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.dto.UserDto;
import ru.vitrix.entity.UserEntity;

public interface UserService {
    void save(UserDto userDto, MultipartFile file);

    boolean existByUsername(String username);

    UserEntity findById(Long id);

    UserDto getById(Long id);

    UserEntity findByUsername(String username);

    UserDto getByUsername(String username);
}
