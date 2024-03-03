package ru.vitrix.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vitrix.entity.UserEntity;
import ru.vitrix.repository.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserRepository userRepository;

    @GetMapping("/panel")
    public String getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            Principal principal,
            Model model
    ) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        List<UserEntity> users = new ArrayList<>(userRepository.findAll(pageRequest).getContent());
        users.removeIf(user -> Objects.equals(user.getUsername(), principal.getName()));

        model.addAttribute("users", users);
        return "admin/panel";
    }

    @PatchMapping("/ban-user/{id}")
    public String banUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElseThrow();
        user.setAccountLocked(true);
        userRepository.save(user);
        return "redirect:/admin/panel";
    }

    @PatchMapping("/unban-user/{id}")
    public String unbanUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElseThrow();
        user.setAccountLocked(false);
        userRepository.save(user);
        return "redirect:/admin/panel";
    }
}
