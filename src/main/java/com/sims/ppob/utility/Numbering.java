package com.sims.ppob.utility;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class Numbering {

    public String InvoiceNumber() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        String dateTimeStr = now.format(formatter);

        return "INV"+dateTimeStr+"-"+randomNumber();
    }

    public String randomNumber() {
        Random random = new Random();
        int number = random.nextInt(9000) + 1000;

        return String.valueOf(number);
    }
}
