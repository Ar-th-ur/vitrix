package ru.vitrix.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vitrix.entity.UserEntity;
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
        UserEntity userEntity = userService.findById(id);
        boolean isOwner = Objects.equals(userEntity.getUsername(), principal.getName());
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("user", userEntity);
        return "user/profile";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        String username = principal.getName();
        UserEntity userEntity = userService.findByUsername(username);
        boolean isOwner = Objects.equals(userEntity.getUsername(), username);

        model.addAttribute("isOwner", isOwner);
        model.addAttribute("user", userEntity);
        return "user/profile";
    }
}
