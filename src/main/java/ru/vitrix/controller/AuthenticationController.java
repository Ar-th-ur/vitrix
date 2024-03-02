package ru.vitrix.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.dto.UserDto;
import ru.vitrix.service.impl.UserServiceImpl;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
@Validated
public class AuthenticationController {
    private final UserServiceImpl userService;

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", defaultValue = "false") boolean loginError,
            Model model
    ) {
        model.addAttribute("error", loginError);
        return "user/login";
    }

    @GetMapping("/registration")
    public String registration(
            @ModelAttribute("user") UserDto userDto,
            @RequestParam(value = "error", defaultValue = "false") boolean registrationError,
            Model model
    ) {
        model.addAttribute("error", registrationError);
        return "user/registration";
    }

    @PostMapping("/registration")
    public String register(
            @ModelAttribute("user") UserDto userDto,
            @RequestParam(value = "file") MultipartFile file
    ) {
        String username = userDto.getUsername();
        if (userService.existByUsername(username)) {
            return "redirect:/auth/registration?error=true";
        }
        userService.save(userDto, file);
        return "redirect:/auth/login";
    }
}
