package ru.vitrix.controller;

import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ConcurrentModel;
import ru.vitrix.dto.PageResponse;
import ru.vitrix.dto.PostDto;
import ru.vitrix.service.PostService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {
    @Mock
    private PostService service;

    @InjectMocks
    private PostController controller;

    @Test
    void posts_ReturnsIndexPage() {
        // given
        var model = new ConcurrentModel();
        var post1 = PostDto.builder()
                .id(1L)
                .title("title 1")
                .build();
        var post2 = PostDto.builder()
                .id(2L)
                .title("title 2")
                .build();
        var pageResponse = PageResponse.<PostDto>builder()
                .content(List.of(post1, post2))
                .build();

        doReturn(pageResponse).when(this.service)
                .findAll("to search", 0, 1);

        // when
        var result = this.controller.posts("to search", 0, 1, model);

        // then
        assertEquals("index", result);
        assertEquals("to search", model.getAttribute("search"));
        assertEquals(pageResponse.getContent(), ((PageResponse) model.getAttribute("page")).getContent());

        verify(this.service).findAll("to search", 0, 1);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void getCretePostPage_RerunsCreatePostPage() {
        // when
        var result = this.controller.getCretePostPage(new PostDto());

        // then
        assertEquals("posts/create", result);

        verifyNoInteractions(this.service);
    }

    @Test
    void createPost_RedirectsPostsPage() {
        // given
        var post = new PostDto();
        var principal = new UserPrincipal("name");
        var file = new MockMultipartFile("file", new byte[]{});

        // when
        var result = this.controller.createPost(post, file, principal);

        // then
        assertEquals("redirect:/posts", result);

        verify(this.service).save(post, "name", file);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void delete_RedirectsToPreviousPage() {
        // given
        var request = new MockHttpServletRequest();
        request.addHeader("Referer", "/previousPage");

        // when
        var result = this.controller.delete(1L, request);

        // then
        assertEquals("redirect:/previousPage", result);

        verify(this.service).deleteById(1L);
        verifyNoMoreInteractions(this.service);
    }

}