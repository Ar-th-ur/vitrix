package ru.vitrix.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.entity.PostEntity;
import ru.vitrix.request.PostRequest;
import ru.vitrix.service.impl.PostServiceImpl;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostServiceImpl service;

    @GetMapping
    public String posts(
            @RequestParam(value = "search", required = false) String search,
            Model model
    ) {
        List<PostEntity> postEntities = service.findAll(search);
        model.addAttribute("search", search);
        model.addAttribute("posts", postEntities);
        return "index";
    }

    @GetMapping("/create")
    public String showCreatePostForm(@ModelAttribute("post") PostRequest postRequest) {
        return "posts/create";
    }

    @PostMapping
    public String createPost(
            @ModelAttribute("post") PostRequest postRequest,
            @RequestParam("file") MultipartFile file,
            Principal principal
    ) {
        String email = principal.getName();
        service.save(postRequest, email, file);
        return "redirect:/posts";
    }

    @DeleteMapping
    public String delete(@RequestParam("postId") UUID id) {
        service.deleteById(id);
        return "redirect:/user/profile";
    }
}
