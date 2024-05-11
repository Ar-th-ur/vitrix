package ru.vitrix.service.impl;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.dto.PostDto;
import ru.vitrix.dto.mapper.PostMapper;
import ru.vitrix.repository.PostRepository;
import ru.vitrix.service.ImageService;
import ru.vitrix.service.UserService;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @Mock
    private ImageService imageService;
    @Mock
    private UserService userService;
    @Mock
    private PostRepository repository;
    @Mock
    private PostMapper mapper;

    @InjectMocks
    private PostServiceImpl service;

    @Test
    @Disabled
    void save_SavesProduct() {
        // given
        var post = new PostDto();
        var username = "name";
        var file = Mockito.mock(MultipartFile.class);

        // when
        this.service.save(post, username, file);

        // then
        verify(this.mapper).toEntity(post);
        verify(this.userService).findByUsername("name");
        verify(this.imageService).fromFile(file);
    }
}