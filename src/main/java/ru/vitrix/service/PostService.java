package ru.vitrix.service;

import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.entity.PostEntity;
import ru.vitrix.request.PostRequest;

import java.util.List;
import java.util.UUID;

public interface PostService {
    void save(PostRequest postRequest, String username, MultipartFile file);

    void deleteById(UUID id);

    List<PostEntity> findAll(String title);

}
