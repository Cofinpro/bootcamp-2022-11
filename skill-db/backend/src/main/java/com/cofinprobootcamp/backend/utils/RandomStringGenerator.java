package com.cofinprobootcamp.backend.utils;

import com.cofinprobootcamp.backend.config.Constants;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Pseudo-static class responsible for random outer ID generation. It has a method to create new
 * random alphanumeric (i.e., base-62) strings of a given length.
 *
 * @author l-rehm
 * @version 1.0
 */
public final class RandomStringGenerator {

    /**
     * Generate a new random {@code String} that can be used as (unique) outer ID for any entity.
     * The length of the string may be set individually, where chance of collision should be low.
     * <br><br>
     * Details on implementation:
     * <br>
     * {@code ThreadLocalRandom} is used internally to avoid
     * <a href="https://plumbr.io/blog/locked-threads/shooting-yourself-in-the-foot-with-random-number-generators">
     * contention issues</a>  in a multithreaded environment (like a Spring web application).
     * See <a href="https://www.baeldung.com/java-thread-local-random">here</a> for practical examples.
     * <br><br>
     * Notes on probabilities:
     * <br>
     * The probability of a collision, i.e. the chance of generating the same string twice, is
     * approximately: {@code n^2 / (2 * q^x)}, with {@code n}: estimated no. of identifiers to generate,
     * {@code q}: distinct symbols in base alphabet, {@code x}: length of the generated IDs.
     * See <a href="https://stackoverflow.com/a/41156">this post</a> for more details.
     * <br>
     * For the case unique user IDs, assuming a total of 2000 users to be generated (i.e. 2000
     * employees, ~10x current number) using 5-chars-long strings would lead to {@code P(collision) =
     * 4.000.000 / 1.832.265.664 => 0,002183}. So over 2000 rolls the chance of a collision would be
     * about 0.2%. This is enough to be error handled, but should not affect performance severely.
     *
     * @param length Determines the length of the {@code String} to be generated
     * @return A new random {@code String} object
     */
    public static String nextOuterId(int length) {
        ThreadLocalRandom random = ThreadLocalRandom.current(); // Get a thread local random (not cryptographically secure -> would be unnecessary her)
        char[] buffer = new char[length];
        for (int i = 0; i < length; i++) {
            int randomCharPosition = random.nextInt(Constants.BASE_62.length()); // val between 0 (inclusive) and length of base (exclusive)
            buffer[i] = Constants.BASE_62.charAt(randomCharPosition);
        }
        return new String(buffer);
    }

    private RandomStringGenerator() {}
}
