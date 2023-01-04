package com.cofinprobootcamp.backend.role.dto;

/**
 * A DTO that is used to pass detailed information about a user role to the application. The necessary information
 * includes the role's internal identifier, its display name and a detailed description of its scope and associated
 * permissions.
 *
 * @param identifier          A {@code String} representation of the role constant identifier
 * @param displayName         A {@code String} representation of the human-understandable display name
 * @param detailedDescription A {@code String} representation of the role's detail information in natural language
 */
public record RoleDetailsOutDTO(String identifier, String displayName, String detailedDescription) {
}
