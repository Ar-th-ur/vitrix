package ru.vitrix.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import ru.vitrix.dto.UserDto;
import ru.vitrix.service.AdminService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
    @Mock
    private AdminService service;

    @InjectMocks
    private AdminController controller;

    @Test
    void getAllUsers_ValidRequest_ReturnsAdminPanelPage() {
        // given
        var pageNumber = 0;
        var pageSize = 2;
        var user1 = UserDto.builder()
                .username("username 1")
                .password("password 1")
                .build();
        var user2 = UserDto.builder()
                .username("username 2")
                .password("password 2")
                .build();
        var users = List.of(user1, user2);
        var model = new ConcurrentModel();

        doReturn(users).when(this.service)
                .findAllUsers(pageNumber, pageSize);

        // when
        var result = this.controller.getAllUsers(pageNumber, pageSize, model);

        // then
        assertEquals("admin/panel", result);
        assertEquals(users, model.getAttribute("users"));

        verify(this.service).findAllUsers(pageNumber, pageSize);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void banUser_ValidRequest_RedirectsAdminPanelPage() {
        // given
        var userId = 0L;

        // when
        var result = this.controller.banUser(userId);

        // then
        assertEquals("redirect:/admin/panel", result);

        verify(this.service).banUserById(userId);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void unbanUser_ValidRequest_RedirectsAdminPanelPage() {
        // given
        var userId = 0L;

        // when
        var result = this.controller.unbanUser(userId);

        // then
        assertEquals("redirect:/admin/panel", result);

        verify(this.service).unbanUserById(userId);
        verifyNoMoreInteractions(this.service);
    }

}