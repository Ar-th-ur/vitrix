package ru.vitrix.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ConcurrentModel;
import ru.vitrix.dto.UserDto;
import ru.vitrix.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationController controller;


    @Test
    void getLoginPage_Error_ReturnsLoginPage() {
        // given
        var model = new ConcurrentModel();

        // when
        var result = this.controller.getLoginPage(true, true, model);

        // then
        assertEquals(true, model.getAttribute("accLocked"));
        assertEquals(true, model.getAttribute("wrongPasOrLogin"));
        assertEquals("user/login", result);

        verifyNoInteractions(this.userService);
    }

    @Test
    void getRegistrationPage_Error_ReturnsRegistrationPage() {
        // given
        var user = new UserDto();
        var model = new ConcurrentModel();

        // when
        var result = this.controller.getRegistrationPage(user, true, model);

        // then
        assertEquals("user/registration", result);
        assertEquals(true, model.getAttribute("error"));

        verifyNoInteractions(this.userService);
    }

    @Test
    void registration_AccountExists_RedirectsRegistrationPageWithError() {
        // given
        var user = UserDto.builder()
                .username("username")
                .build();
        var file = new MockMultipartFile("file", new byte[]{});

        doReturn(true).when(this.userService)
                .existByUsername("username");

        // when
        var result = this.controller.registration(user, file);

        // then
        assertEquals("redirect:/auth/registration?error=true", result);

        verify(this.userService).existByUsername("username");
        verifyNoMoreInteractions(this.userService);
    }

    @Test
    void registration_AccountNotExists_RedirectsLoginPage() {
        var user = UserDto.builder()
                .username("new username")
                .build();
        var file = new MockMultipartFile("file", new byte[]{});

        doReturn(false).when(this.userService)
                .existByUsername("new username");

        // when
        var result = this.controller.registration(user, file);

        // then
        assertEquals("redirect:/auth/login", result);

        verify(this.userService).existByUsername("new username");
        verify(this.userService).save(user, file);
        verifyNoMoreInteractions(this.userService);
    }


}