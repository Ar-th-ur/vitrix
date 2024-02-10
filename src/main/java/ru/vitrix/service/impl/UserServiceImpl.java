package ru.vitrix.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.request.UserRequest;
import ru.vitrix.request.mapper.UserMapper;
import ru.vitrix.entity.ImageEntity;
import ru.vitrix.entity.Role;
import ru.vitrix.entity.UserEntity;
import ru.vitrix.repository.UserRepository;
import ru.vitrix.service.UserService;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(UserRequest userRequest, MultipartFile file) {
        var user = userMapper.toEntity(userRequest);
        var password = userRequest.getPassword();
        try {
            ImageEntity avatar = ImageEntity.from(file);
            user.setAvatar(avatar);
        } catch (IOException e) {
            log.error("Failed to receive bytes from file", e);
        }

        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        userRepository.saveAndFlush(user);

        log.info("Saving new user {}", user);
    }

    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void update(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public UserEntity findById(UUID id) {
        return userRepository.findById(id).orElseThrow();
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }
}
