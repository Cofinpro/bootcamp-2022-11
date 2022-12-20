package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.enums.UserRights;

import java.util.List;

public class RoleDirector {
    public static Role roleFromSpecification(String shortname, String descriptiveName, List<UserRights> userRights) {
        return Role.builder()
                .name(shortname)
                .descriptiveName(descriptiveName)
                .userRights(userRights)
                .build();
    }
}
