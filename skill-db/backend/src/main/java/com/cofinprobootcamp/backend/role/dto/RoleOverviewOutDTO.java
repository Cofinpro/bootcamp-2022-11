package com.cofinprobootcamp.backend.role.dto;

import com.cofinprobootcamp.backend.role.Role;
import com.cofinprobootcamp.backend.role.RoleDirector;

import java.util.List;

public record RoleOverviewOutDTO(String shortName, String descriptiveName, List<String> userRights) {
    public RoleOverviewOutDTO(Role role) {
        this(
                role.getName(),
                role.getDescriptiveName(),
                RoleDirector.simplifiedUserRights(role.getName())
        );
    }
}
