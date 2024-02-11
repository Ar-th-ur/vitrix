package ru.vitrix.dto.response.entity;

import lombok.Getter;
import lombok.Setter;
import ru.vitrix.dto.response.base.BaseAuditResponse;
import ru.vitrix.entity.auxiliary.Role;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserResponse extends BaseAuditResponse {
    private String username;
    private boolean isEnabled;
    private List<PostResponse> posts;
    private ImageResponse avatar;
    private Set<Role> roles;
}
