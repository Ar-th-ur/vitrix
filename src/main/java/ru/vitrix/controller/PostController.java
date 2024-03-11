package ru.vitrix.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.vitrix.dto.PageResponse;
import ru.vitrix.dto.PostDto;
import ru.vitrix.exception.FileException;
import ru.vitrix.service.PostService;

import java.security.Principal;
import java.util.Locale;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService service;

    @GetMapping
    public String posts(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "size", defaultValue = "30") int size,
            Model model
    ) {
        PageResponse<PostDto> pageResponse = service.findAll(search, pageNo, size);
        model.addAttribute("search", search);
        model.addAttribute("page", pageResponse);
        return "index";
    }

    @GetMapping("/create")
    public String showCreatePostForm(@ModelAttribute("post") PostDto postDto) {
        return "posts/create";
    }


    @PostMapping
    public String createPost(
            @ModelAttribute("post") PostDto postDto,
            @RequestParam("file") MultipartFile file,
            Principal principal
    ) {
        var username = principal.getName();
        service.save(postDto, username, file);
        return "redirect:/posts";
    }

    @DeleteMapping
    public String delete(@RequestParam("postId") Long id) {
        service.deleteById(id);
        return "redirect:/user/profile";
    }
}
