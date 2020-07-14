package com.example.weatherapp;

import java.util.ArrayList;

public class CityInfo {
    private ArrayList<WeatherInfo> consolidated_weather;
    private String title;

    public CityInfo(ArrayList<WeatherInfo> consolidated_weather, String title, int woeid) {
        this.consolidated_weather = consolidated_weather;
        this.title = title;
    }

    public ArrayList<WeatherInfo> getConsolidated_weather() {
        return consolidated_weather;
    }

    public String getTitle() {
        return title;
    }
}
