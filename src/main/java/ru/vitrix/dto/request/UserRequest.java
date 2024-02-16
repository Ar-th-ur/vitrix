package ru.vitrix.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
public class UserRequest {
    @Size(max = 18)
    private String username;
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^[\\w@$!?]+$", message = "{validation.password.regex.message}")
    private String password;
}
