package com.serverless;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Rate {

    private String source;
    private String target;

    private String value;
    private String date;

    public Rate(String source, String target, String value, String date) {
        this.source = source;
        this.target = target;
        this.value = value;
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static Rate[] seeder() {
        // Create an array to hold 20 instances of Rate
        Rate[] rates = new Rate[20];

        // Sample official currency codes
        String[] currencyCodes = { "USD", "EUR", "GBP", "JPY", "CAD", "AUD" };

        // Get current date
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Generate 20 instances of Rate class
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            String sourceCurrency = currencyCodes[random.nextInt(currencyCodes.length)];
            String targetCurrency = currencyCodes[random.nextInt(currencyCodes.length)];

            // Ensure source and target currencies are different
            while (sourceCurrency.equals(targetCurrency)) {
                targetCurrency = currencyCodes[random.nextInt(currencyCodes.length)];
            }

            // Generate a random conversion rate between 1 and 10 (for demonstration
            // purposes)
            double conversionRate = (random.nextDouble() * 9) + 1;

            // Generate a random date within the past year
            int daysAgo = random.nextInt(365);
            calendar.add(Calendar.DAY_OF_YEAR, -daysAgo);
            Date randomDate = calendar.getTime();
            calendar.setTime(currentDate); // Reset calendar to current date

            // Format the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(randomDate);

            // Create Rate instance and add it to the array
            rates[i] = new Rate(sourceCurrency, targetCurrency, String.valueOf(conversionRate), dateString);
        }

        return rates;
    }

    public static Rate findClosestRate(Rate[] rates, String source, String target, String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date givenDate;
        givenDate = dateFormat.parse(date);

        long closestDateDifference = Long.MAX_VALUE;
        Rate closestRate = null;

        for (Rate rate : rates) {
            if (rate.getSource().equals(source) && rate.getTarget().equals(target)) {
                try {
                    Date rateDate = dateFormat.parse(rate.getDate());
                    long dateDifference = Math.abs(rateDate.getTime() - givenDate.getTime());

                    if (dateDifference < closestDateDifference) {
                        closestDateDifference = dateDifference;
                        closestRate = rate;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return closestRate;
    }

    public static Rate[] SEEDS = {
            new Rate("EUR", "CAD", "7.03737423661313", "2023-01-12"),
            new Rate("AUD", "GBP", "3.2410994327923026", "2023-12-19"),
            new Rate("USD", "CAD", "1.1440411719524615", "2023-03-07"),
            new Rate("AUD", "EUR", "1.9472464671831764", "2023-01-06"),
            new Rate("JPY", "GBP", "4.1608969153660045", "2023-03-18"),
            new Rate("CAD", "USD", "8.653393784961672", "2023-02-09"),
            new Rate("CAD", "AUD", "4.728517757009156", "2023-11-29"),
            new Rate("CAD", "GBP", "1.5958783912603525", "2023-06-29"),
            new Rate("EUR", "CAD", "4.351080514059994", "2023-09-17"),
            new Rate("AUD", "CAD", "4.315384264468068", "2023-01-13"),
            new Rate("JPY", "AUD", "9.666415336358716", "2023-03-16"),
            new Rate("AUD", "GBP", "9.444527023806128", "2023-10-29"),
            new Rate("JPY", "GBP", "9.326553778422138", "2023-08-22"),
            new Rate("GBP", "EUR", "8.716107263899271", "2023-05-15"),
            new Rate("JPY", "GBP", "2.5848729909027908", "2023-07-17"),
            new Rate("EUR", "CAD", "1.0822903383309899", "2023-08-08"),
            new Rate("JPY", "CAD", "9.468663142382987", "2023-04-04"),
            new Rate("USD", "JPY", "7.201187258809182", "2023-03-18"),
            new Rate("USD", "JPY", "7.201187258809182", "2023-06-21"),
            new Rate("CAD", "EUR", "3.0058463421344657", "2023-02-13"),
            new Rate("JPY", "GBP", "1.0920223498525439", "2023-11-03")
    };

}
