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

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService service;

    @GetMapping
    public String posts(@RequestParam(value = "search", defaultValue = "") String search,
                        @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                        @RequestParam(value = "size", defaultValue = "30") int size,
                        Model model) {
        PageResponse<PostDto> pageResponse = service.findAll(search, pageNumber, size);
        model.addAttribute("search", search);
        model.addAttribute("page", pageResponse);
        return "index";
    }

    @GetMapping("/create")
    public String showCreatePostForm(@ModelAttribute("post") PostDto postDto) {
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
