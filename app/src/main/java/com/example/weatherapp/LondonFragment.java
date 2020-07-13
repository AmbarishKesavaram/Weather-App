package com.example.weatherapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LondonFragment extends Fragment {
    TextView londonHeaderText, londonWeatherStateName, londonDayOfWeek, londonMaximumTemperature, londonActualTemperature, londonMinimumTemperature, londonHumidity, londonPredictability;
    ImageView todayWeatherIcon;
    private RecyclerView recyclerView;
    RecyclerAdaptor recyclerAdaptor;
    CityInfo london;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_london, container, false);
        retroFitLondon();
        return view;
    }

    public void retroFitLondon() {

        Api api = RetrofitInstance.getRetrofit().create(Api.class);
        final Call<CityInfo> callLondon = api.getLondonDetails();
        callLondon.enqueue(new Callback<CityInfo>() {
            @Override
            public void onResponse(Call<CityInfo> call, Response<CityInfo> response) {
                london = response.body();

                setTextView(londonHeaderText, R.id.londonHead, london.getTitle());
                setImageView(todayWeatherIcon, R.id.londonWeatherIcon, london.getConsolidated_weather().get(0).getWeather_state_abbr());
                setTextView(londonDayOfWeek, R.id.londonDayOfWeek, WeatherSupport.dayOfWeek(london.getConsolidated_weather().get(0).getApplicable_date()));
                setTextView(londonWeatherStateName, R.id.londonWeatherState, london.getConsolidated_weather().get(0).getWeather_state_name());
                setTextView(londonMaximumTemperature, R.id.londonMaxTemp, WeatherSupport.convertDouble(london.getConsolidated_weather().get(0).getMax_temp()));
                setTextView(londonActualTemperature, R.id.londonActualTemp, WeatherSupport.convertDouble(london.getConsolidated_weather().get(0).getThe_temp()));
                setTextView(londonMinimumTemperature, R.id.londonMinTemp, WeatherSupport.convertDouble(london.getConsolidated_weather().get(0).getMin_temp()));
                setTextView(londonHumidity, R.id.londonHumidity, WeatherSupport.convertInt(london.getConsolidated_weather().get(0).getHumidity()));
                setTextView(londonPredictability, R.id.londonPredictability, WeatherSupport.convertInt(london.getConsolidated_weather().get(0).getPredictability()));

                generateView(london.getConsolidated_weather(), getView());
            }

            @Override
            public void onFailure(Call<CityInfo> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTextView(TextView textView, int id, String text) {

        textView = getView().findViewById(id);
        textView.setText(text);
    }

    private void setImageView(ImageView imageView, int id, String weatherState) {

        imageView = getView().findViewById(id);
        Picasso.get().load("https://www.metaweather.com/static/img/weather/ico/" + weatherState + ".ico").into(imageView);

    }

    public void generateView(ArrayList<WeatherInfo> weatherInfos, View view) {
        weatherInfos.remove(0);
        recyclerAdaptor = new RecyclerAdaptor(weatherInfos, getActivity().getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView = view.findViewById(R.id.londonRecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdaptor);
        recyclerAdaptor.setOnClickListener(onClickListener);
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            Bundle b = new Bundle();
            b.putParcelable("weather", london.getConsolidated_weather().get(position));
            b.putString("city","London");
            PredictionFragment fragment = new PredictionFragment();
            fragment.setArguments(b);

            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
    };
}