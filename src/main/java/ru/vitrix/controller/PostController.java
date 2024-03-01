package ru.vitrix.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.dto.request.PostRequest;
import ru.vitrix.dto.response.PageResponse;
import ru.vitrix.dto.response.entity.PostResponse;
import ru.vitrix.service.impl.PostServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostServiceImpl service;

    @GetMapping
    public String posts(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "size", defaultValue = "20") int size,
            Model model
    ) {
        PageResponse<PostResponse> pageResponse = service.getAll(search, pageNo, size);
        model.addAttribute("search", search);
        model.addAttribute("page", pageResponse);
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
    public String delete(@RequestParam("postId") Long id) {
        service.deleteById(id);
        return "redirect:/user/profile";
    }
}
