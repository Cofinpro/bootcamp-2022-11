package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.enums.UserRights;

import java.util.List;

public record RoleDTO(
        Long id,
        String name,
        List<UserRights> userRights
) {

    public RoleDTO(Role role) {
        this(role.getId(), role.getName(), role.getUserRights());
    }
}
