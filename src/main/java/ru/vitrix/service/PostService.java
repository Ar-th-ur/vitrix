package ru.vitrix.service;

import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.dto.PageResponse;
import ru.vitrix.dto.PostDto;

public interface PostService {
    void save(PostDto postRequest, String username, MultipartFile file);

    void deleteById(Long id);

    PageResponse<PostDto> findAll(String title, int pageNumber, int size);
}
