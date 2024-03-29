package ru.vitrix.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"bytes"})
@Table(name = "images")
public class ImageEntity extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_size", nullable = false)
    private Long size;

    @Column(name = "contentType", nullable = false)
    private String contentType;

    @Column(name = "data", nullable = false)
    private byte[] bytes;
}
