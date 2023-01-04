package com.cofinprobootcamp.backend.role.dto;

import java.util.List;

/**
 * A DTO that is used to pass overview information about all user roles to the application.
 *
 * @param allRoles A {@code List} of {@code RoleDetailsOutDTO} records
 */
public record RoleOverviewOutDTO(List<RoleDetailsOutDTO> allRoles) {
}