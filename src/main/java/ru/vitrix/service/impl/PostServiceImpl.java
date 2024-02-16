package ru.vitrix.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.dto.mapper.PostMapper;
import ru.vitrix.dto.request.PostRequest;
import ru.vitrix.dto.response.PageResponse;
import ru.vitrix.dto.response.entity.PostResponse;
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
    private final PostMapper mapper;

    private final PostRepository postRepository;
    private final UserServiceImpl userService;

    public PostResponse save(PostRequest postRequest, String username, MultipartFile file) {
        var post = mapper.toEntity(postRequest);
        var owner = userService.findByUsername(username);
        try {
            ImageEntity imageEntity = ImageEntity.from(file);
            post.setImage(imageEntity);
        } catch (IOException e) {
            log.error("Failed to receive bytes from file", e);
        }

        post.setOwner(owner);
        owner.getPosts().add(post);
        postRepository.save(post);
        userService.update(owner);

        log.info("Saving new post {}", postRequest);
        return mapper.toResponse(post);
    }

    public void deleteById(UUID id) {
        postRepository.deleteById(id);
    }

    public PageResponse<PostResponse> getAll(String title, int pageNo, int size) {
        PageRequest pageRequest = PageRequest.of(pageNo, size);
        Page<PostEntity> page = postRepository.findAllByTitle(title, pageRequest);
        List<PostResponse> content = page.getContent().stream().map(mapper::toResponse).toList();

        return PageResponse.<PostResponse>builder()
                .last(page.isLast())
                .content(content)
                .pageSize(size)
                .pageNumber(pageNo)
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }
}
