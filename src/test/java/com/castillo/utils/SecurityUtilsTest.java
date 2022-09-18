package com.castillo.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SecurityUtilsTest {
    @Test
    void generateSecureRandomString() {
        String value1 = SecurityUtils.generateSecureRandomString(14);
        String value2 = SecurityUtils.generateSecureRandomString(14);
        assertNotEquals(value1, value2);
    }
}