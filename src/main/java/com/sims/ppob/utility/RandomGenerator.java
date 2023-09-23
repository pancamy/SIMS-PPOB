package com.sims.ppob.utility;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomGenerator {

    public String randomNumber() {
        Random randomObj = new Random();
        int number = randomObj.nextInt(9000) + 1000;

        return String.valueOf(number);
    }

    public String randomString(Integer len) {
        int length = len;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();

        Random randomObj = new Random();

        for (int i = 0; i < length; i++) {
            int index = randomObj.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }

        return randomString.toString();
    }
}
