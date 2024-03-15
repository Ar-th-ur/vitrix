package ru.vitrix.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.vitrix.dto.UserDto;
import ru.vitrix.dto.mapper.UserMapper;
import ru.vitrix.entity.UserEntity;
import ru.vitrix.exception.NotFoundException;
import ru.vitrix.repository.UserRepository;
import ru.vitrix.service.AdminService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> findAllUsers(int pageNumber, int pageSize) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = userRepository.findAll(pageRequest);
        var users = new ArrayList<>(page.getContent());

        var currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        users.removeIf(user -> Objects.equals(user.getUsername(), currentAuthentication.getName()));

        return users.stream().map(userMapper::toDto).toList();
    }

    @Override
    public void banUserById(Long id) {
        var user = findById(id);
        user.setAccountLocked(true);
        userRepository.save(user);
    }

    @Override
    public void unbanUserById(Long id) {
        var user = findById(id);
        user.setAccountLocked(false);
        userRepository.save(user);
    }

    private UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User with id %d not found".formatted(id))
        );
    }
}
