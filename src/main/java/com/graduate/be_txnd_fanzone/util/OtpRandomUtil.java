package com.graduate.be_txnd_fanzone.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class OtpRandomUtil {

    private static final SecureRandom random = new SecureRandom();

    public static String generateOtp(int length) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        return sb.toString();
    }
}
