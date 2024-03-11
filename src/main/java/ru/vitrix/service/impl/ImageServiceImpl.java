package ru.vitrix.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.entity.ImageEntity;
import ru.vitrix.exception.FileException;
import ru.vitrix.service.ImageService;

import java.io.IOException;
import java.util.Arrays;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Value("${spring.servlet.multipart.max-file-size}")
    private DataSize maxFileSize;
    private final String[] allowedContentTypes = {IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE};

    @Override
    public ImageEntity fromFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileException("File can not be empty");
        }
        if (file.getSize() > maxFileSize.toBytes()) {
            throw new FileException("Maximum file size is %d mb".formatted(maxFileSize.toMegabytes()));
        }
        String contentType = file.getContentType();
        if (!Arrays.asList(allowedContentTypes).contains(contentType)) {
            throw new FileException("Not allowed file content type");
        }

        try {
            return ImageEntity.builder()
                    .fileName(file.getName())
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .bytes(file.getBytes())
                    .build();
        } catch (IOException e) {
            throw new FileException("Failed to receive bytes from the file");
        }
    }
}
