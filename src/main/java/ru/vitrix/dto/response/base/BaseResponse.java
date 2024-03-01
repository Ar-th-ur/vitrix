package ru.vitrix.dto.response.base;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class BaseResponse {
    private Long id;
}
