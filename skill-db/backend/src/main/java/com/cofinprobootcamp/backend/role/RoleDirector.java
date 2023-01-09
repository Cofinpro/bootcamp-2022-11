package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.role.dto.RoleDetailsOutDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Role director
 *
 * @version 2.0
 */
public class RoleDirector {
    public static RoleDetailsOutDTO roleDetailsViewFromEnumType(StandardRoles role) {
        return new RoleDetailsOutDTO(
                role.name(),
                role.toString(),
                role.getRoleDetailsDescription()
        );
    }

    public static List<RoleDetailsOutDTO> roleOverviewFromEnum() {
        List<RoleDetailsOutDTO> allRoles = new LinkedList<>();
        for (var role : StandardRoles.getAllDefinedValues()) {
            allRoles.add(roleDetailsViewFromEnumType(role));
        }
        return allRoles;
    }
}
