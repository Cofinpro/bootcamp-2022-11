package com.cofinprobootcamp.backend.config;

public final class Constants {

    /**
     * This constant defines the set of (distinct) symbols available in the alphabet when generating
     * random strings. This specific set including all upper case letters, all lower case letters
     * and all digits is referred to as "base 62".
     */
    public static final String BASE_62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static final int USER_OUTER_ID_LENGTH = 5;
    public static final int PROFILE_OUTER_ID_LENGTH = 6;

    /**
     * The prefix to be used to identify roles internally while handling access.
     */
    public static final String ROLE_PREFIX = "PRIVILEGE_";

    /**
     * {@code scp} - the Scope claim identifies the privileges associated with the JWT
     */
    public static final String JWT_CLAIM_SCP = "scp";

    public static final String JWT_ISSUER_NAME = "Cofinpro Bootcamp 2022";

    private Constants() {
    }
}
