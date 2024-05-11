package ru.vitrix.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vitrix.service.AdminService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService service;

    @GetMapping("/panel")
    public String getAllUsers(Model model, Principal principal) {
        var users = service.findAllUsersWithout(principal.getName());
        model.addAttribute("users", users);
        return "admin/panel";
    }

    @PatchMapping("/ban-user/{id}")
    public String banUser(@PathVariable Long id) {
        service.banUserById(id);
        return "redirect:/admin/panel";
    }

    @PatchMapping("/unban-user/{id}")
    public String unbanUser(@PathVariable Long id) {
        service.unbanUserById(id);
        return "redirect:/admin/panel";
    }
}
