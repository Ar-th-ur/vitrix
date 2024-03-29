package ru.vitrix.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vitrix.service.UserService;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile/{id}")
    public String profileById(@PathVariable Long id, Principal principal, Model model) {
        var userDto = userService.getById(id);
        var isOwner = false;
        if (principal != null) {
            isOwner = Objects.equals(userDto.getUsername(), principal.getName());
        }

        model.addAttribute("isOwner", isOwner);
        model.addAttribute("user", userDto);
        return "user/profile";
    }

    @GetMapping("/profile")
    public String profileByPrincipal(Principal principal, Model model) {
        var username = principal.getName();
        var userDto = userService.getByUsername(username);
        var isOwner = Objects.equals(userDto.getUsername(), username);

        model.addAttribute("isOwner", isOwner);
        model.addAttribute("user", userDto);
        return "user/profile";
    }
}
