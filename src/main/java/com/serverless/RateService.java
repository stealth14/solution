package com.serverless;

import java.text.ParseException;

public class RateService {

    public static Rate getRate(String source, String target, String date) throws ParseException {

        Rate[] rates = Rate.SEEDS;

        return Rate.findClosestRate(rates, source, target, date);
    }

}
