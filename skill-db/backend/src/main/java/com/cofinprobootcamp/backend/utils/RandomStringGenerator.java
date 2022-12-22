package com.cofinprobootcamp.backend.utils;

import com.cofinprobootcamp.backend.config.Constants;

import java.security.SecureRandom;

public final class RandomStringGenerator {

    public static String nextOuterId(int length) {
        SecureRandom random = new SecureRandom(); // with standard algorithm: SHA1PRNG
        char[] buffer = new char[length];
        for (int i = 0; i < length; i++) {
            int randomCharPosition = random.nextInt(Constants.BASE_62.length());
            buffer[i] = Constants.BASE_62.charAt(randomCharPosition);
        }
        return new String(buffer);
    }

    private RandomStringGenerator() {}
}
