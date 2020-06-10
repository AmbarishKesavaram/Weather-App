package com.example.weatherapp;

import java.util.List;

public class CityInfo {
    private List<WeatherInfo> consolidated_weather;
    private String title;
    private int woeid;

    public CityInfo(List<WeatherInfo> consolidated_weather, String title, int woeid) {
        this.consolidated_weather = consolidated_weather;
        this.title = title;
        this.woeid = woeid;
    }

}
