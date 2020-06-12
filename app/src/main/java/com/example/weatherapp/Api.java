package com.example.weatherapp;


import retrofit2.Call;
import retrofit2.http.GET;
public interface Api {

    String BASE_URL ="https://www.metaweather.com/api/location/";

    @GET("3534")
    Call<CityInfo> getMontrealDetails();

    @GET("2459115")
    Call<CityInfo> getNewYorkDetails();

    @GET("44418")
    Call<CityInfo> getLondonDetails();

    @GET("615702")
    Call<CityInfo> getParisDetails();

    @GET("1103816")
    Call<CityInfo> getMelbourneDetails();

    @GET("2295414")
    Call<CityInfo> getHyderabadDetails();

}
