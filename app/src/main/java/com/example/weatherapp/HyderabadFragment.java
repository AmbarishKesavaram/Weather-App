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

public class HyderabadFragment extends Fragment {
    TextView hyderabadHeaderText, hyderabadWeatherStateName, hyderabadDayOfWeek, hyderabadMaximumTemperature, hyderabadActualTemperature, hyderabadMinimumTemperature, hyderabadHumidity, hyderabadPredictability;
    ImageView todayWeatherIcon;
    private RecyclerView recyclerView;
    RecyclerAdaptor recyclerAdaptor;
    CityInfo hyderabad;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hyderabad, container, false);
        retroFitHyderabad();
        return view;
    }

    public void retroFitHyderabad() {

        Api api = RetrofitInstance.getRetrofit().create(Api.class);
        final Call<CityInfo> callHyderabad = api.getHyderabadDetails();
        callHyderabad.enqueue(new Callback<CityInfo>() {
            @Override
            public void onResponse(Call<CityInfo> call, Response<CityInfo> response) {
                hyderabad = response.body();

                setTextView(hyderabadHeaderText, R.id.hyderabadHead, hyderabad.getTitle());
                setImageView(todayWeatherIcon, R.id.hyderabadWeatherIcon, hyderabad.getConsolidated_weather().get(0).getWeather_state_abbr());
                setTextView(hyderabadDayOfWeek, R.id.hyderabadDayOfWeek, WeatherSupport.dayOfWeek(hyderabad.getConsolidated_weather().get(0).getApplicable_date()));
                setTextView(hyderabadWeatherStateName, R.id.hyderabadWeatherState, hyderabad.getConsolidated_weather().get(0).getWeather_state_name());
                setTextView(hyderabadMaximumTemperature, R.id.hyderabadMaxTemp, WeatherSupport.convertDouble(hyderabad.getConsolidated_weather().get(0).getMax_temp()));
                setTextView(hyderabadActualTemperature, R.id.hyderabadActualTemp, WeatherSupport.convertDouble(hyderabad.getConsolidated_weather().get(0).getThe_temp()));
                setTextView(hyderabadMinimumTemperature, R.id.hyderabadMinTemp, WeatherSupport.convertDouble(hyderabad.getConsolidated_weather().get(0).getMin_temp()));
                setTextView(hyderabadHumidity, R.id.hyderabadHumidity, WeatherSupport.convertInt(hyderabad.getConsolidated_weather().get(0).getHumidity()));
                setTextView(hyderabadPredictability, R.id.hyderabadPredictability, WeatherSupport.convertInt(hyderabad.getConsolidated_weather().get(0).getPredictability()));

                generateView(hyderabad.getConsolidated_weather(), getView());
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
        recyclerView = view.findViewById(R.id.hyderabadRecyclerView);
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
            b.putParcelable("weather", hyderabad.getConsolidated_weather().get(position));
            b.putString("city","Hyderabad");
            PredictionFragment fragment = new PredictionFragment();
            fragment.setArguments(b);

            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
    };
}