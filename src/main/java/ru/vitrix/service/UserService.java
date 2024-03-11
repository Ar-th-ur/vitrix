package ru.vitrix.service;

import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.dto.UserDto;
import ru.vitrix.entity.UserEntity;
import ru.vitrix.exception.FileException;

public interface UserService {
    UserDto save(UserDto userDto, MultipartFile file);

    boolean existByUsername(String username);

    UserDto update(UserEntity userEntity);

    UserEntity findById(Long id);

    UserDto getById(Long id);

    UserEntity findByUsername(String username);

    UserDto getByUsername(String username);
}
