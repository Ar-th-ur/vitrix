package ru.vitrix.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.dto.PageResponse;
import ru.vitrix.dto.PostDto;
import ru.vitrix.service.impl.PostServiceImpl;

import java.security.Principal;
import java.util.List;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostServiceImpl service;

    private final static String[] allowedContentTypes = {IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE};

    @GetMapping
    public String posts(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "size", defaultValue = "30") int size,
            Model model
    ) {
        PageResponse<PostDto> pageResponse = service.getAll(search, pageNo, size);
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
            Principal principal,
            Model model
    ) {
        // TODO: написать какие типы файлов поддерживаются и переписать с ExceptionController
        if (!fileMatch(file)) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Данный тип файла не поддерживается");
            return "posts/create";
        }
        // TODO: @Value(${max-file-size}) возвращает String что не удобно
//        if (file.getSize() > maxFileSize) {
//            model.addAttribute("error", true);
//            model.addAttribute("errorMessage", "Максимальный размер файла " + maxFileSize);
//            return "posts/create";
//        }
        var username = principal.getName();
        service.save(postDto, username, file);
        return "redirect:/posts";
    }

    @DeleteMapping
    public String delete(@RequestParam("postId") Long id) {
        service.deleteById(id);
        return "redirect:/user/profile";
    }

    private boolean fileMatch(MultipartFile file) {
        if (file == null) {
            return false;
        }
        var contentType = file.getContentType();
        if (contentType == null) {
            return false;
        }
        return List.of(allowedContentTypes).contains(contentType);
    }
}
