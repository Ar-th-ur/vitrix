package ru.vitrix.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.entity.ImageEntity;
import ru.vitrix.exception.FileException;
import ru.vitrix.repository.ImageRepository;
import ru.vitrix.service.ImageService;

import java.io.IOException;
import java.util.Arrays;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Value("${spring.servlet.multipart.max-file-size}")
    private DataSize maxFileSize;
    private final String[] allowedContentTypes = {IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE};

    @Override
    public ImageEntity fromFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileException("file.error.empty");
        }
        if (file.getSize() > maxFileSize.toBytes()) {
            throw new FileException("file.error.max_size");
        }
        String contentType = file.getContentType();
        if (!Arrays.asList(allowedContentTypes).contains(contentType)) {
            throw new FileException("file.error.not_allowed_type");
        }

        try {
            return ImageEntity.builder()
                    .fileName(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .bytes(file.getBytes())
                    .build();
        } catch (IOException e) {
            throw new FileException("file.error.failed_to_read");
        }
    }

    @Override
    public ImageEntity findById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }
}
