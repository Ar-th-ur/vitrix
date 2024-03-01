package ru.vitrix.dto.response.base;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class BaseAuditResponse extends BaseResponse {
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
