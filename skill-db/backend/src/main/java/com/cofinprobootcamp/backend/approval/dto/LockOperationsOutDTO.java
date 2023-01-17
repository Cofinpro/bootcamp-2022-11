package com.cofinprobootcamp.backend.approval.dto;

/**
 * A DTO that is used to pass information about a stored operation to lock a user to the application.
 * The necessary information includes the user whose should be locked and the user who initiated that operation.
 *
 * @param target          A {@code String} representation of the outerId of the user whose role should be changed
 * @param initiator          A {@code String} representation of the email of the user who decided to change the role of the target
 */
public record LockOperationsOutDTO(String target, String initiator) {
}
