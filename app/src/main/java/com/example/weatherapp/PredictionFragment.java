package com.example.weatherapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class PredictionFragment extends Fragment {
    TextView  weatherStateName,header, maximumTemperature,dayOfWeek, actualTemperature, minimumTemperature, humidity, predictability;
    ImageView weatherIcon;
    WeatherInfo weatherInfo;

    public PredictionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prediction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        weatherInfo =getArguments().getParcelable("weather");


        generateView();
    }

    public void generateView() {
        String head =getArguments().getString("city");
        setTextView(weatherStateName,R.id.weatherState,weatherInfo.getWeather_state_name());
        setImageView(weatherIcon,R.id.weatherIcon,weatherInfo.getWeather_state_abbr());
        setTextView(maximumTemperature,R.id.maxTemp,WeatherSupport.convertDouble(weatherInfo.getMax_temp()));
        setTextView(actualTemperature,R.id.actualTemp,WeatherSupport.convertDouble(weatherInfo.getThe_temp()));
        setTextView(minimumTemperature,R.id.minTemp,WeatherSupport.convertDouble(weatherInfo.getMin_temp()));
        setTextView(humidity,R.id.humidity,WeatherSupport.convertInt(weatherInfo.getHumidity()));
        setTextView(predictability,R.id.predictability,WeatherSupport.convertInt(weatherInfo.getPredictability()));
        setTextView(dayOfWeek,R.id.prDayOfWeek,WeatherSupport.dayOfWeek(weatherInfo.getApplicable_date()));
        setTextView(header,R.id.head,head);
    }

    private void setTextView(TextView textView, int id, String text) {

        textView = getView().findViewById(id);
        textView.setText(text);
    }

    private void setImageView(ImageView imageView, int id, String weatherState) {

        imageView = getView().findViewById(id);
        Picasso.get().load("https://www.metaweather.com/static/img/weather/ico/" + weatherState + ".ico").into(imageView);

    }

}