package ru.vitrix.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.dto.PageResponse;
import ru.vitrix.dto.PostDto;
import ru.vitrix.dto.mapper.PostMapper;
import ru.vitrix.entity.ImageEntity;
import ru.vitrix.entity.PostEntity;
import ru.vitrix.repository.PostRepository;
import ru.vitrix.service.PostService;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostMapper mapper;

    private final PostRepository postRepository;
    private final UserServiceImpl userService;

    public PostDto save(PostDto postDto, String username, MultipartFile file) {
        PostEntity post = mapper.toEntity(postDto);
        var owner = userService.findByUsername(username);
        try {
            ImageEntity imageEntity = ImageEntity.from(file);
            post.setImage(imageEntity);
        } catch (IOException e) {
            log.error("Failed to receive bytes from file", e);
        }

        post.setOwner(owner);
        postRepository.save(post);
        owner.getPosts().add(post);
        userService.update(owner);

        log.info("Saving new post {}", postDto);
        return mapper.toDto(post);
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    public PageResponse<PostDto> getAll(String title, int pageNo, int size) {
        PageRequest pageRequest = PageRequest.of(pageNo, size);
        Page<PostEntity> page;
        if (title.isBlank()) {
            page = postRepository.findAll(pageRequest);
        } else {
            page = postRepository.findAllByTitle(title, pageRequest);
        }
        List<PostDto> content = page.getContent().stream().map(mapper::toDto).toList();

        return PageResponse.<PostDto>builder()
                .last(page.isLast())
                .content(content)
                .pageSize(size)
                .pageNumber(pageNo)
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }
}
