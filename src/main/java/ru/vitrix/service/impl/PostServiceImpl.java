package ru.vitrix.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
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

    private final PostRepository repository;
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

        log.info("Saving new postEntity {}", imageEntity);
        return mapper.toDto(postEntity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public List<PostDto> findAll(String filter) {
        List<PostEntity> posts;
        if (filter.isBlank()) {
            posts = repository.findAll();
        } else {
            posts = repository.findAllByTitle("%" + filter + "%");
        }

        return posts.stream().map(mapper::toDto).toList();
    }
}
