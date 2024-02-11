package ru.vitrix.dto.response.entity;

import lombok.Getter;
import lombok.Setter;
import ru.vitrix.dto.response.base.BaseAuditResponse;

import java.util.UUID;

@Getter
@Setter
public class PostResponse extends BaseAuditResponse {
    private String title;
    private UUID ownerId;
    private ImageResponse image;
}
