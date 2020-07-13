package com.example.weatherapp;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherInfo implements Parcelable {
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

    protected WeatherInfo(Parcel in) {
        weather_state_name = in.readString();
        weather_state_abbr = in.readString();
        applicable_date = in.readString();
        min_temp = in.readDouble();
        max_temp = in.readDouble();
        the_temp = in.readDouble();
        humidity = in.readInt();
        predictability = in.readInt();
    }

    public static final Creator<WeatherInfo> CREATOR = new Creator<WeatherInfo>() {
        @Override
        public WeatherInfo createFromParcel(Parcel in) {
            return new WeatherInfo(in);
        }

        @Override
        public WeatherInfo[] newArray(int size) {
            return new WeatherInfo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(weather_state_name);
        dest.writeString(weather_state_abbr);
        dest.writeString(applicable_date);
        dest.writeDouble(min_temp);
        dest.writeDouble(max_temp);
        dest.writeDouble(the_temp);
        dest.writeInt(humidity);
        dest.writeInt(predictability);
    }
}
