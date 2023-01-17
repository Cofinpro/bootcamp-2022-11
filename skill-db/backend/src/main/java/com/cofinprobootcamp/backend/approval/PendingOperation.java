package com.cofinprobootcamp.backend.approval;

/**
 * This interface represents an operation (returning any type {@code T}) that needs to be approved
 * before it can be resolved. Until then its status is pending.
 *
 * @author l-rehm
 * @version 1.0
 * @param <T> The return type of the {@code PendingOperation}
 */
@FunctionalInterface
public interface PendingOperation<T> {

    /**
     * Resolve the pending operation by executing it and returning its designated value
     * @return An object of type {@code T} that results from the operation
     */
    T resolve();
}
