package com.cofinprobootcamp.backend.role.dto;

import com.cofinprobootcamp.backend.role.Role;
import com.cofinprobootcamp.backend.role.RoleDirector;

import java.util.Map;

public record RoleOutDTO(String shortName, String descriptiveName, Map<String, Map<String, String>> userRights) {

    public RoleOutDTO(Role role) {
        this(
                role.getName(),
                role.getDescriptiveName(),
                RoleDirector.roleUserRightsToDTOMap(role)
        );
    }
}
