package ru.vitrix.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.unit.DataSize;
import ru.vitrix.exception.FileException;
import ru.vitrix.repository.ImageRepository;

import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {
    @Mock
    private ImageRepository repository;
    @Mock
    private DataSize dataSize = DataSize.ofBytes(5);

    @InjectMocks
    private ImageServiceImpl service;

    @Test
    void fromFile_ValidRequest_ReturnsIMageEntity() {
        // given
        var multipartFile = new MockMultipartFile(
                "file",
                "original",
                IMAGE_PNG_VALUE,
                new byte[]{0, 0});

        // when
        var result = this.service.fromFile(multipartFile);

        // then
        assertEquals("original", result.getFileName());
        assertEquals(IMAGE_PNG_VALUE, result.getContentType());
        assertArrayEquals(new byte[]{0, 0}, result.getBytes());

        verifyNoInteractions(this.repository);
    }

    @Test
    void fromFile_FileIsEmpty_ThrowsFileException() {
        // given
        var multipartFile = new MockMultipartFile(
                "file",
                "original",
                IMAGE_PNG_VALUE,
                new byte[]{});

        // when
        assertThrows(FileException.class, () -> this.service.fromFile(multipartFile));

        // then
        verifyNoInteractions(this.repository);
    }

    @Test
    void fromFile_TooLargeFileSize_ThrowsFileException() {
        // given
        var multipartFile = new MockMultipartFile(
                "file",
                "original",
                IMAGE_PNG_VALUE,
                new byte[]{0, 0, 0, 0, 0, 0, 0, 0});

        // when
        assertThrows(FileException.class, () -> this.service.fromFile(multipartFile));

        // then
        verifyNoInteractions(this.repository);
    }

    @Test
    void fromFile_NotSupportedFileContentType_ThrowsFileException() {
        // given
        var multipartFile = new MockMultipartFile(
                "file",
                "original",
                IMAGE_GIF_VALUE,
                new byte[]{});

        // when
        assertThrows(FileException.class, () -> this.service.fromFile(multipartFile));

        // then
        verifyNoInteractions(this.repository);
    }
}