package ru.vitrix.dto.response.base;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BaseAuditResponse extends BaseResponse {
    private LocalDate createdAt;
    private LocalDate modifiedAt;
}
