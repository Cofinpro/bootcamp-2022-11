package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.enums.UserRights;

import java.util.List;

public record RoleDTO(
        String name,
        List<UserRights> userRights
) {

    public RoleDTO(Role role) {
        this(role.getName(), role.getUserRights());
    }
}
