package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.role.dto.RoleDetailsOutDTO;
import com.cofinprobootcamp.backend.role.dto.RoleOverviewOutDTO;

import java.util.LinkedList;
import java.util.List;

public class RoleDirector {
    public static RoleDetailsOutDTO roleDetailsViewFromEnumType(StandardRoles role) {
        return new RoleDetailsOutDTO(
                role.name(),
                role.toString(),
                role.getRoleDetailsDescription(),
                role.getAssociatedPrivileges().stream()
                        .map(UserPrivileges::getPrivilegeDetailsDescription)
                        .toList()
        );
    }

    public static RoleOverviewOutDTO roleOverviewFromEnumType(StandardRoles role) {
        return new RoleOverviewOutDTO(
                role.name(),
                role.toString(),
                role.getRoleDetailsDescription()
        );
    }

    public static List<RoleOverviewOutDTO> allRoleOverviewsFromEnum() {
        List<RoleOverviewOutDTO> allRoles = new LinkedList<>();
        for (var role : StandardRoles.getAllDefinedValues()) {
            allRoles.add(roleOverviewFromEnumType(role));
        }
        return allRoles;
    }
}
