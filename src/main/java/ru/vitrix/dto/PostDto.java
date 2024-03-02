package ru.vitrix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostDto {
    private Long id;
    private String title;
    private Long imageId;
    private Long ownerId;
    private LocalDateTime createdAt;
}
