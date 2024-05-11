package ru.vitrix.service;

import ru.vitrix.dto.UserDto;

import java.util.List;

public interface AdminService {
    List<UserDto> findAllUsersWithout(String username);

    void banUserById(Long id);

    void unbanUserById(Long id);
}
