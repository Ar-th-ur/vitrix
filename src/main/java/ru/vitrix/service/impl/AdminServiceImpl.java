package ru.vitrix.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vitrix.dto.UserDto;
import ru.vitrix.dto.mapper.UserMapper;
import ru.vitrix.entity.UserEntity;
import ru.vitrix.exception.UserNotFoundException;
import ru.vitrix.repository.UserRepository;
import ru.vitrix.service.AdminService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> findAllUsersWithout(String username) {
        var users = userRepository.findAll();
        users.removeIf(user -> Objects.equals(user.getUsername(), username));

        return users.stream().map(userMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void banUserById(Long id) {
        var user = findById(id);
        user.setAccountLocked(true);
    }

    @Override
    @Transactional
    public void unbanUserById(Long id) {
        var user = findById(id);
        user.setAccountLocked(false);
    }

    private UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with id %d not found".formatted(id))
        );
    }
}
