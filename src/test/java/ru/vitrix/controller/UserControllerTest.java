package ru.vitrix.controller;

import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import ru.vitrix.dto.UserDto;
import ru.vitrix.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService service;

    @InjectMocks
    private UserController controller;

    @Test
    void profileById_IsOwner_ReturnsProfilePage() {
        // given
        var user = UserDto.builder()
                .id(1L)
                .username("username")
                .build();
        var principal = new UserPrincipal("username");
        var model = new ConcurrentModel();

        doReturn(user).when(this.service)
                .getById(1L);

        // when
        var result = this.controller.profileById(1L, principal, model);

        // then
        assertEquals(true, model.getAttribute("isOwner"));
        assertEquals(user, model.getAttribute("user"));
        assertEquals("user/profile", result);

        verify(this.service).getById(1L);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void profileById_IsNotOwner_ReturnsProfilePage() {
        // given
        var user = UserDto.builder()
                .id(1L)
                .username("username")
                .build();
        var principal = new UserPrincipal("another username");
        var model = new ConcurrentModel();

        doReturn(user).when(this.service)
                .getById(1L);

        // when
        var result = this.controller.profileById(1L, principal, model);

        // then
        assertEquals(false, model.getAttribute("isOwner"));
        assertEquals(user, model.getAttribute("user"));
        assertEquals("user/profile", result);

        verify(this.service).getById(1L);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void profileById_NullPrincipal_ReturnsProfilePage() {
        // given
        var user = UserDto.builder()
                .id(1L)
                .username("username")
                .build();
        var model = new ConcurrentModel();

        doReturn(user).when(this.service)
                .getById(1L);

        // when
        var result = this.controller.profileById(1L, null, model);

        // then
        assertEquals(false, model.getAttribute("isOwner"));
        assertEquals(user, model.getAttribute("user"));
        assertEquals("user/profile", result);

        verify(this.service).getById(1L);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void profileByPrincipal_IsOwner_ReturnsProfilePage() {
        // given
        var user = UserDto.builder()
                .id(1L)
                .username("username")
                .build();
        var principal = new UserPrincipal("username");
        var model = new ConcurrentModel();

        doReturn(user).when(this.service)
                .getByUsername("username");

        // when
        var result = this.controller.profileByPrincipal(principal, model);

        // then
        assertEquals(true, model.getAttribute("isOwner"));
        assertEquals(user, model.getAttribute("user"));
        assertEquals("user/profile", result);

        verify(this.service).getByUsername("username");
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void profileByPrincipal_isNotOwner_ReturnsProfilePage() {
        // given
        var user = UserDto.builder()
                .id(1L)
                .username("username")
                .build();
        var principal = new UserPrincipal("another username");
        var model = new ConcurrentModel();

        doReturn(user).when(this.service)
                .getByUsername("another username");

        // when
        var result = this.controller.profileByPrincipal(principal, model);

        // then
        assertEquals(false, model.getAttribute("isOwner"));
        assertEquals(user, model.getAttribute("user"));
        assertEquals("user/profile", result);

        verify(this.service).getByUsername("another username");
        verifyNoMoreInteractions(this.service);
    }

}