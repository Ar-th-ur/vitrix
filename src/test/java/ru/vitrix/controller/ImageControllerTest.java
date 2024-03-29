package ru.vitrix.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.vitrix.entity.ImageEntity;
import ru.vitrix.service.ImageService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {
    @Mock
    private ImageService service;

    @InjectMocks
    private ImageController controller;

    @Test
    void getImage_ImageExists_ReturnsResponseOfImage() throws IOException {
        // given
        var image = ImageEntity.builder()
                .id(1L)
                .contentType(IMAGE_PNG_VALUE)
                .size(3L)
                .bytes(new byte[]{1, 1, 1})
                .build();

        doReturn(image).when(this.service)
                .findById(1L);

        // when
        ResponseEntity<?> result = this.controller.getImage(1L);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(IMAGE_PNG_VALUE, result.getHeaders().get(HttpHeaders.CONTENT_TYPE).get(0));
        assertArrayEquals(new byte[]{1, 1, 1}, ((InputStreamResource) result.getBody()).getContentAsByteArray());

        verify(this.service).findById(1L);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void getImage_ImageDoesntExist_ReturnsResponseOfNull() {
        // given
        doReturn(null).when(this.service)
                .findById(1L);

        // when
        ResponseEntity<?> result = this.controller.getImage(1L);


        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody());

        verify(this.service).findById(1L);
        verifyNoMoreInteractions(this.service);
    }
}