package com.okchain.crypto.keystore;

import java.security.SecureRandom;


final class SecureRandomUtils {

    private static final SecureRandom SECURE_RANDOM;

    static {
        SECURE_RANDOM = new SecureRandom();
    }

    static SecureRandom secureRandom() {
        return SECURE_RANDOM;
    }

    private SecureRandomUtils() {
    }
}
