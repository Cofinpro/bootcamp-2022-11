package com.cofinprobootcamp.backend.approval;

/**
 * This interface represents a manager that is capable of evaluating and eventually approving
 * {@link PendingOperation}s of the given type.
 *
 * @param <P> The type of {@link PendingOperation} to handle
 */
@FunctionalInterface
public interface OperationApprovalManager<P extends PendingOperation<?> > {

    /**
     * Tries to approve the given pending operation and resolve it, if approved, or decline
     * to execute it.
     *
     * @param pendingOperation The {@link PendingOperation} to evaluate for approval
     * @return {@code true}, if the pending operation was approved and {@code false} otherwise
     */
    boolean approve(P pendingOperation);
}
