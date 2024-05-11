package ru.vitrix.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.dto.PageResponse;
import ru.vitrix.dto.PostDto;
import ru.vitrix.service.PostService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService service;

    @GetMapping
    public String posts(@RequestParam(value = "search", defaultValue = "") String search,
                        Model model) {
        List<PostDto> posts = service.findAll(search);
        model.addAttribute("search", search);
        model.addAttribute("posts", posts);
        return "index";
    }

    @GetMapping("/create")
    public String getCretePostPage(@ModelAttribute("post") PostDto postDto) {
        return "posts/create";
    }


    @PostMapping
    public String createPost(@ModelAttribute("post") PostDto postDto,
                             @RequestParam("file") MultipartFile file,
                             Principal principal) {
        var username = principal.getName();
        service.save(postDto, username, file);
        return "redirect:/posts";
    }

    @DeleteMapping
    public String delete(@RequestParam("postId") Long id, HttpServletRequest request) {
        service.deleteById(id);
        var referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

}
