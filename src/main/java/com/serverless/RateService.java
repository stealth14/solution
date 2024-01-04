package com.serverless;

import java.text.ParseException;

public class RateService {

    public static Rate getRate(String source, String target, String date) throws ParseException {

        Rate[] rates = Rate.SEEDS;

        Rate result = Rate.findClosestRate(rates, source, target, date);

        if (result == null) {
            // If no direct rate found, search for triangular conversion
            for (Rate rate : rates) {
                // Check if there's an intermediary rate that connects source to target
                if (rate.getSource().equals(source)) {
                    Rate intermediaryRate = Rate.findClosestRate(rates, rate.getTarget(), target, date);
                    if (intermediaryRate != null) {
                        // Calculate the triangular conversion rate
                        double sourceToIntermediary = Double.parseDouble(rate.getValue());
                        double intermediaryToTarget = Double.parseDouble(intermediaryRate.getValue());

                        double resultValue = sourceToIntermediary * intermediaryToTarget;

                        return new Rate(source, target, String.valueOf(resultValue), date);
                    }
                }
            }
            // If no triangular conversion found, return null
            return null;
        }

        return result;
    }

}
