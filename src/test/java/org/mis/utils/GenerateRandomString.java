package org.mis.utils;

import net.bytebuddy.utility.RandomString;

import java.security.SecureRandom;
import java.util.Random;

public class GenerateRandomString {



    //private final Random RANDOM = new SecureRandom();
    //private final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static void main(String[] args) {
        GenerateRandomString randomStringExample = new GenerateRandomString();
        String zoneName = randomStringExample.generateRandomString(5);

        System.out.println(zoneName);
    }
    public static String generateRandomString(int length) {
        final Random RANDOM = new SecureRandom();
        final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }
}
