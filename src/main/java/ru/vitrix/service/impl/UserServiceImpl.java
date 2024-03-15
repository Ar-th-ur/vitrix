package ru.vitrix.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.dto.UserDto;
import ru.vitrix.dto.mapper.UserMapper;
import ru.vitrix.entity.Role;
import ru.vitrix.entity.UserEntity;
import ru.vitrix.exception.NotFoundException;
import ru.vitrix.repository.UserRepository;
import ru.vitrix.service.ImageService;
import ru.vitrix.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ImageService imageService;

    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    @Override
    @Transactional
    public UserDto save(UserDto userDto, MultipartFile file) {
        var user = mapper.toEntity(userDto);
        var password = userDto.getPassword();

        var image = imageService.fromFile(file);
        user.setAvatar(image);

        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        log.info("Saving new user {}", user);
        return mapper.toDto(user);
    }


    @Override
    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDto update(UserEntity userEntity) {
        var savedUser = userRepository.save(userEntity);
        return mapper.toDto(savedUser);
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("User with id \"%s\" not found".formatted(id))
                );
    }

    @Override
    public UserDto getById(Long id) {
        return mapper.toDto(findById(id));
    }


    @Override
    public UserDto getByUsername(String username) {
        return mapper.toDto(findByUsername(username));
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with username \"%s\" not found".formatted(username))
                );
    }

}
