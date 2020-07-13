package com.example.weatherapp;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherSupport {
    @SuppressLint("DefaultLocale")
    public static String convertDouble(double dataDouble){
        return String.format("%.2f", dataDouble) +"°C";
    }

    public static String convertInt(int dataInt){
        return dataInt + "%";
    }

    @SuppressLint("SimpleDateFormat")
    public static String dayOfWeek(String dateInput) {
        String dayOfWeek;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;

        try {
            date1 = dateFormat.parse(dateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dayOfWeek = new SimpleDateFormat("EEEE").format(date1);
        dayOfWeek += ", " + new SimpleDateFormat("d").format(date1);

        return dayOfWeek;
    }
}
