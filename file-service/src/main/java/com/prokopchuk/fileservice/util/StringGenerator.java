package com.prokopchuk.fileservice.util;

import java.security.SecureRandom;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringGenerator {

    private static final String USED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String generateString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(USED_CHARS.charAt(SECURE_RANDOM.nextInt(USED_CHARS.length())));
        }

        return sb.toString();
    }

}
