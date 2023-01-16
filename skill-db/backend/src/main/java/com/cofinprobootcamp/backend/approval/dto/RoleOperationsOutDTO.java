package com.cofinprobootcamp.backend.approval.dto;

/**
 * A DTO that is used to pass information about a stored operation to change the role of a user to the application.
 * The necessary information includes the user whose role should be changed, the user who initiated that change and
 * the identifier of that new role.
 *
 * @param target          A {@code String} representation of the outerId of the user whose role should be changed
 * @param initiator          A {@code String} representation of the email of the user who decided to change the role of the target
 * @param roleId          A {@code String} representation of the role constant identifier
 */
public record RoleOperationsOutDTO(String target, String initiator, String roleId) {
}