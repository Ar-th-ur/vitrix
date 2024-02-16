package ru.vitrix.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vitrix.dto.response.entity.UserResponse;
import ru.vitrix.service.impl.UserServiceImpl;

import java.security.Principal;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable UUID id, Principal principal, Model model) {
        UserResponse userResponse = userService.getById(id);
        boolean isOwner = Objects.equals(userResponse.getUsername(), principal.getName());

        model.addAttribute("isOwner", isOwner);
        model.addAttribute("user", userResponse);
        return "user/profile";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal Principal principal, Model model) {
        String username = principal.getName();
        UserResponse userResponse = userService.getByUsername(username);
        boolean isOwner = Objects.equals(userResponse.getUsername(), username);

        model.addAttribute("isOwner", isOwner);
        model.addAttribute("user", userResponse);
        return "user/profile";
    }
}
