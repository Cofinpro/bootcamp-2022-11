package com.cofinprobootcamp.backend.config;

/**
 * Pseudo-static class that serves as a "storage" for the various regular expressions
 * that may be used to analyze input {@code String}s send to a web end point.
 *
 * @author l-rehm
 * @version 1.1
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
     * A valid email address consists of at least one allowed symbol (letter [A-Za-z] or
     * digit or certain special characters like dot [.] and dash [-]) followed by an "@"
     * followed by at least another symbol followed by a dot followed by at least another
     * symbol.
     */
    public static final String VALID_MAIL_ADDRESS = "^\\w[\\w.\\-]+@[\\w.\\-]+\\.\\w+$";

    /**
     * A regular expression that can be used to specify the date format for printing
     * and {@code String} representation.
     */
    public static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    public static final int MINIMUM_PASSWORD_LENGTH = 8;

    public static final String BASE_URL = "api/v1";

    // Private constructor to emulate a 'static class'
    private Regex() {
    }
}
