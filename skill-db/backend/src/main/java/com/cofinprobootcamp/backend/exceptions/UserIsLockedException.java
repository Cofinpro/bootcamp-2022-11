package com.cofinprobootcamp.backend.exceptions;

import org.springframework.security.authentication.LockedException;

public class UserIsLockedException extends LockedException {
    private static final String standardMessage = "Dieser Nutzeraccount ist gesperrt!";

    /**
     * Constructs a <code>UserIsLockedException</code> with a standard message.
     */
    public UserIsLockedException() {
        super(standardMessage);
    }

    /**
     * Constructs a <code>UserIsLockedException</code> with the specified message.
     * @param msg the detail message.
     */
    public UserIsLockedException(String msg) {
        super(standardMessage + " " + msg);
    }

    /**
     * Constructs a <code>UserIsLockedException</code> with the specified message and root
     * cause.
     * @param msg the detail message.
     * @param cause root cause
     */
    public UserIsLockedException(String msg, Throwable cause) {
        super(standardMessage + " " + msg, cause);
    }
}
