package ru.vitrix.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vitrix.service.ImageService;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getImage(@PathVariable Long id) {
        var image = imageService.findById(id);
        if (image == null) {
            return ResponseEntity.ok(null);
        }

        var body = new InputStreamResource(new ByteArrayInputStream(image.getBytes()));
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(body);
    }
}
