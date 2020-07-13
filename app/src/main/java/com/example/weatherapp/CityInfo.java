package com.example.weatherapp;

import java.util.ArrayList;

public class CityInfo {
    private ArrayList<WeatherInfo> consolidated_weather;
    private String title;
    private int woeid;

    public CityInfo(ArrayList<WeatherInfo> consolidated_weather, String title, int woeid) {
        this.consolidated_weather = consolidated_weather;
        this.title = title;
        this.woeid = woeid;
    }

    public ArrayList<WeatherInfo> getConsolidated_weather() {
        return consolidated_weather;
    }

    public String getTitle() {
        return title;
    }

    public int getWoeid() {
        return woeid;
    }
}
