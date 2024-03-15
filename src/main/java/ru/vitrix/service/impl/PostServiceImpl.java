package ru.vitrix.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.dto.PageResponse;
import ru.vitrix.dto.PostDto;
import ru.vitrix.dto.mapper.PostMapper;
import ru.vitrix.entity.PostEntity;
import ru.vitrix.repository.PostRepository;
import ru.vitrix.service.ImageService;
import ru.vitrix.service.PostService;
import ru.vitrix.service.UserService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final ImageService imageService;
    private final UserService userService;

    private final PostRepository postRepository;
    private final PostMapper mapper;


    public PostDto save(PostDto postDto, String username, MultipartFile file) {
        PostEntity post = mapper.toEntity(postDto);
        var owner = userService.findByUsername(username);

        var imageEntity = imageService.fromFile(file);
        post.setImage(imageEntity);

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

    public PageResponse<PostDto> findAll(String title, int pageNo, int size) {
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
