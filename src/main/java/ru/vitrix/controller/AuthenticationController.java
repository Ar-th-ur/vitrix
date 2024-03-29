package ru.vitrix.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.dto.UserDto;
import ru.vitrix.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
@Validated
public class AuthenticationController {
    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(value = "accLocked", defaultValue = "false") boolean accLocked,
                               @RequestParam(value = "wrongPasOrLogin", defaultValue = "false") boolean wrongPasOrLogin,
                               Model model) {
        model.addAttribute("accLocked", accLocked);
        model.addAttribute("wrongPasOrLogin", wrongPasOrLogin);
        return "user/login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto,
                                      @RequestParam(value = "error", defaultValue = "false") boolean registrationError,
                                      Model model) {
        model.addAttribute("error", registrationError);
        return "user/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") UserDto userDto,
                               @RequestParam(value = "file") MultipartFile file) {
        var username = userDto.getUsername();
        if (userService.existByUsername(username)) {
            return "redirect:/auth/registration?error=true";
        }
        userService.save(userDto, file);
        return "redirect:/auth/login";
    }
}
