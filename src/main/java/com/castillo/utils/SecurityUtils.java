package com.castillo.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class SecurityUtils {
    private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    private static final SecureRandom random = new SecureRandom();

    public static String generateSecureRandomString(int bytes) {
        byte[] buffer = new byte[bytes];
        random.nextBytes(buffer);
        return encoder.encodeToString(buffer);
    }
}
