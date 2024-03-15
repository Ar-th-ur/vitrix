package ru.vitrix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vitrix.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/panel")
    public String getAllUsers(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                              @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
                              Model model) {
        var users = adminService.findAllUsers(pageNumber, pageSize);
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
