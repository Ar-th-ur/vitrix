package ru.vitrix.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vitrix.dto.mapper.UserMapper;
import ru.vitrix.entity.UserEntity;
import ru.vitrix.exception.UserNotFoundException;
import ru.vitrix.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper mapper;

    @InjectMocks
    private AdminServiceImpl service;

    @Test
    void findAllUsersWithout_ReturnsListOfUsers() {
        // given
        List<UserEntity> users = LongStream.range(1, 4)
                .mapToObj(v -> UserEntity.builder()
                        .id(v)
                        .username("username %d".formatted(v))
                        .build())
                .collect(Collectors.toCollection(ArrayList::new));
        var username = "username 2";

        Mockito.doReturn(users).when(this.userRepository)
                .findAll();

        // when
        var result = this.service.findAllUsersWithout(username);

        // then
        assertEquals(2, result.size());

        verify(this.userRepository).findAll();
        verifyNoMoreInteractions(this.userRepository);
    }

    @Test
    void banUserById_UserPresent() {
        // given
        var user = UserEntity.builder()
                .id(1L)
                .isAccountLocked(false)
                .build();

        doReturn(Optional.of(user)).when(this.userRepository)
                .findById(1L);

        //when
        this.service.banUserById(1L);

        //then
        assertTrue(user.isAccountLocked());

        verify(this.userRepository).findById(1L);
        verifyNoMoreInteractions(this.userRepository);
    }

    @Test
    void banUserById_UserNotFound_ThrowsException() {
        // when
        assertThrows(UserNotFoundException.class, () -> this.service.banUserById(1L));

        //then
        verify(this.userRepository).findById(1L);
        verifyNoMoreInteractions(this.userRepository);
    }

    @Test
    void unbanUserById_UserPresent() {
        // given
        var user = UserEntity.builder()
                .id(1L)
                .isAccountLocked(true)
                .build();

        doReturn(Optional.of(user)).when(this.userRepository)
                .findById(1L);

        //when
        this.service.unbanUserById(1L);

        //then
        assertFalse(user.isAccountLocked());

        verify(this.userRepository).findById(1L);
        verifyNoMoreInteractions(this.userRepository);
    }

    @Test
    void unbanUserById_UserNotFound_ThrowsException() {
        // when
        assertThrows(UserNotFoundException.class, () -> this.service.unbanUserById(1L));

        //then
        verify(this.userRepository).findById(1L);
        verifyNoMoreInteractions(this.userRepository);
    }
}