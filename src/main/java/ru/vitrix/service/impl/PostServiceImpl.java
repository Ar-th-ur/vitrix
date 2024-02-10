package ru.vitrix.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.request.PostRequest;
import ru.vitrix.request.mapper.PostMapper;
import ru.vitrix.entity.ImageEntity;
import ru.vitrix.entity.PostEntity;
import ru.vitrix.repository.PostRepository;
import ru.vitrix.service.PostService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;

    private final PostRepository repository;
    private final UserServiceImpl userService;

    public void save(PostRequest postRequest, String username, MultipartFile file) {
        var post = postMapper.toEntity(postRequest);
        var owner = userService.findByUsername(username);
        try {
            ImageEntity imageEntity = ImageEntity.from(file);
            post.setImageEntity(imageEntity);
        } catch (IOException e) {
            log.error("Failed to receive bytes from file", e);
        }

        post.setOwner(owner);
        owner.getPosts().add(post);
        repository.save(post);
        userService.update(owner);

        log.info("Saving new post {}", postRequest);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public List<PostEntity> findAll(String title) {
        if (title == null || title.isBlank()) {
            return repository.findAll();
        }
        return repository.findAllByTitle(title);
    }
}
