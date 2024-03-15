package ru.vitrix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostDto {
    private Long id;
    private String title;
    private Long imageId;
    private Long ownerId;
    private LocalDateTime createdAt;
    private boolean inDay;
}
