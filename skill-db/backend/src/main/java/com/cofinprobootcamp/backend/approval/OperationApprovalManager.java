package com.cofinprobootcamp.backend.approval;

import javax.validation.constraints.NotNull;

/**
 * This interface represents a manager that is capable of evaluating and eventually approving
 * {@link PendingOperation}s of the given type.
 *
 * @author l-rehm
 * @version 1.0
 * @param <P> The type of {@code PendingOperation} to handle
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
