package ru.vitrix.service;

import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.dto.PageResponse;
import ru.vitrix.dto.PostDto;
import ru.vitrix.exception.FileException;

public interface PostService {
    PostDto save(PostDto postRequest, String username, MultipartFile file);

    void deleteById(Long id);

    PageResponse<PostDto> findAll(String title, int pageNumber, int size);
}
