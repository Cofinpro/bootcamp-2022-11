package com.cofinprobootcamp.backend.role.dto;

import java.util.List;

import com.cofinprobootcamp.backend.role.UserPrivileges;

/**
 * A DTO that is used to pass detailed information about a user role to the application. The necessary information
 * includes the role's internal identifier, its display name and a detailed description of its scope and associated
 * permissions, including a full list of user privileges in human-understandable form.
 *
 * @param identifier          A {@code String} representation of the role constant identifier
 * @param displayName         A {@code String} representation of the human-understandable display name
 * @param detailedDescription A {@code String} representation of the role's detail information in natural language
 * @param privileges          A {@link List} of {@code String}s representing {@link UserPrivileges}
 */
public record RoleDetailsOutDTO(String identifier, String displayName, String detailedDescription,
                                List<String> privileges) {
}
