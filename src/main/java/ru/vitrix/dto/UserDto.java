package ru.vitrix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.vitrix.entity.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private String username;
    private String password;
    private Long avatarId;
    private Boolean isAccountLocked;
    private LocalDateTime createdAt;
    private List<PostDto> posts;
    private Set<Role> roles;
}
