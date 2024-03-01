package ru.vitrix.service;

import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.dto.request.PostRequest;
import ru.vitrix.dto.response.PageResponse;
import ru.vitrix.dto.response.entity.PostResponse;

public interface PostService {
    PostResponse save(PostRequest postRequest, String username, MultipartFile file);

    void deleteById(Long id);

    PageResponse<PostResponse> getAll(String title, int pageNumber, int size);

}
