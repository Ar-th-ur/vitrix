package ru.vitrix.service;

import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto save(PostDto postRequest, String username, MultipartFile file);

    void deleteById(Long id);

    List<PostDto> findAll(String title);
}
