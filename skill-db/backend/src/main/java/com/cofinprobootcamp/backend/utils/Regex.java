package com.cofinprobootcamp.backend.utils;

/**
 * Pseudo-static class that serves as a "storage" for the various regular expressions
 * that may be used to analyze input {@code String}s send to a web end point.
 *
 * @author l-rehm
 * @version 1.0
 */
public final class Regex {
    /*
     * Constants
     * Would be way more elegant as an enum, but Annotations require compile time constants,
     * which only include primitive types and Strings (see: https://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.28)
     */

    /**
     * A regular expression that can be used by a matcher to find valid phone numbers.
     * A valid phone number consists of 11 to 13 digits and no other symbols.
     */
    public static final String VALID_PHONE_NUMBER = "\\d{11,13}"; // Possibly even better regex: https://stackoverflow.com/a/54829462

    /**
     * A regular expression that can be used by a matcher to find valid email addresses.
     * A valid email address consists of at least one symbol followed by an "@" followed
     * by at least another symbol followed by a dot followed by at least another symbol.
     */
    public static final String VALID_MAIL_ADDRESS = "[\\w.]+@\\w+\\.\\w+"; // "[^@]+@[^@.]+\\.[^@]+" may be better to exclude additional @ symbols

    // Private constructor to emulate a 'static class'
    private Regex() {
    }
}
