package ru.vitrix.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vitrix.dto.UserDto;
import ru.vitrix.entity.UserEntity;
import ru.vitrix.repository.UserRepository;
import ru.vitrix.service.AdminService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/panel")
    public String getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            Model model
    ) {
        var users = adminService.findAll(pageNumber, pageSize);
        model.addAttribute("users", users);
        return "admin/panel";
    }

    @PatchMapping("/ban-user/{id}")
    public String banUser(@PathVariable Long id) {
        adminService.banUserById(id);
        return "redirect:/admin/panel";
    }

    @PatchMapping("/unban-user/{id}")
    public String unbanUser(@PathVariable Long id) {
        adminService.unbanUserById(id);
        return "redirect:/admin/panel";
    }
}
