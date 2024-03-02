package ru.vitrix.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"bytes"})
@Table(name = "images")
public class ImageEntity extends BaseAuditEntity {

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private Long size;

    @Column(name = "contentType")
    private String contentType;

    @Column(name = "data")
    private byte[] bytes;

    public static ImageEntity from(MultipartFile file) throws IOException {
        return ImageEntity.builder()
                .fileName(file.getName())
                .contentType(file.getContentType())
                .size(file.getSize())
                .bytes(file.getBytes())
                .build();
    }
}
