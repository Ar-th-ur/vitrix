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
import ru.vitrix.entity.PostEntity;
import ru.vitrix.repository.PostRepository;
import ru.vitrix.service.ImageService;
import ru.vitrix.service.PostService;
import ru.vitrix.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final ImageService imageService;
    private final UserService userService;

    private final PostRepository postRepository;
    private final PostMapper mapper;

    @Override
    @Transactional
    public PostDto save(PostDto postDto, String username, MultipartFile file) {
        var postEntity = mapper.toEntity(postDto);
        var owner = userService.findByUsername(username);
        var imageEntity = imageService.fromFile(file);

        postEntity.setImage(imageEntity);
        postEntity.setOwner(owner);
        owner.getPosts().add(postEntity);

        postRepository.save(postEntity);
        log.info("Saving new postEntity {}", imageEntity);

        return mapper.toDto(postEntity);
    }

    @Transactional
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public PageResponse<PostDto> findAll(String filter, int pageNo, int size) {
        var pageRequest = PageRequest.of(pageNo, size);
        Page<PostEntity> page;
        if (filter.isBlank()) {
            page = postRepository.findAll(pageRequest);
        } else {
            page = postRepository.findAllByTitle("%" + filter + "%", pageRequest);
        }
        var content = page.getContent().stream().map(mapper::toDto).toList();

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
