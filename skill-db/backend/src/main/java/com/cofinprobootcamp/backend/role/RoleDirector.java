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
                role.getRoleDetailsDescription()
        );
    }

    public static RoleOverviewOutDTO roleOverviewFromEnum() {
        List<RoleDetailsOutDTO> allRoles = new LinkedList<>();
        for (var role : StandardRoles.values()) {
            allRoles.add(roleDetailsViewFromEnumType(role));
        }
        return new RoleOverviewOutDTO(allRoles);
    }
}
