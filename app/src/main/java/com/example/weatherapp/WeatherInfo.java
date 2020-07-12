package com.example.weatherapp;

public class WeatherInfo {
    private String weather_state_name;
    private String weather_state_abbr;
    private String applicable_date;
    private double min_temp;
    private double max_temp;
    private double the_temp;
    private int humidity;
    private int predictability;

    public WeatherInfo(String weather_state_name, String weather_state_abbr, String applicable_date, double min_temp, double max_temp, double the_temp, int humidity, int predictability) {
        this.weather_state_name = weather_state_name;
        this.weather_state_abbr = weather_state_abbr;
        this.applicable_date = applicable_date;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.the_temp = the_temp;
        this.humidity = humidity;
        this.predictability = predictability;
    }
    public String getWeather_state_name() {
        return weather_state_name;
    }

    public String getApplicable_date() {
        return applicable_date;
    }

    public String getWeather_state_abbr() {
        return weather_state_abbr;
    }

    public double getMin_temp() {
        return min_temp;
    }

    public double getMax_temp() {
        return max_temp;
    }

    public double getThe_temp() {
        return the_temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPredictability() {
        return predictability;
    }
}
