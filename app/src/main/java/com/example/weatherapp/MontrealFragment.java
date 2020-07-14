package com.example.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MontrealFragment extends Fragment {
    TextView montrealHeaderText, montrealWeatherStateName, montrealDayOfWeek, montrealMaximumTemperature, montrealActualTemperature, montrealMinimumTemperature, montrealHumidity, montrealPredictability;
    ImageView todayWeatherIcon;
    private RecyclerView recyclerView;
    RecyclerAdaptor recyclerAdaptor;
    CityInfo montreal;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_montreal, container, false);
        retroFitMontreal();
        return view;
    }

    public void retroFitMontreal() {

        Api api = RetrofitInstance.getRetrofit().create(Api.class);
        final Call<CityInfo> callMontreal = api.getMontrealDetails();
        callMontreal.enqueue(new Callback<CityInfo>() {
            @Override
            public void onResponse(Call<CityInfo> call, Response<CityInfo> response) {
                montreal = response.body();

                setTextView(montrealHeaderText, R.id.montrealHead, montreal.getTitle());
                setImageView(todayWeatherIcon, R.id.montrealWeatherIcon, montreal.getConsolidated_weather().get(0).getWeather_state_abbr());
                setTextView(montrealDayOfWeek, R.id.montrealDayOfWeek, WeatherSupport.dayOfWeek(montreal.getConsolidated_weather().get(0).getApplicable_date()));
                setTextView(montrealWeatherStateName, R.id.montrealWeatherState, montreal.getConsolidated_weather().get(0).getWeather_state_name());
                setTextView(montrealMaximumTemperature, R.id.montrealMaxTemp, WeatherSupport.convertDouble(montreal.getConsolidated_weather().get(0).getMax_temp()));
                setTextView(montrealActualTemperature, R.id.montrealActualTemp, WeatherSupport.convertDouble(montreal.getConsolidated_weather().get(0).getThe_temp()));
                setTextView(montrealMinimumTemperature, R.id.montrealMinTemp, WeatherSupport.convertDouble(montreal.getConsolidated_weather().get(0).getMin_temp()));
                setTextView(montrealHumidity, R.id.montrealHumidity, WeatherSupport.convertInt(montreal.getConsolidated_weather().get(0).getHumidity()));
                setTextView(montrealPredictability, R.id.montrealPredictability, WeatherSupport.convertInt(montreal.getConsolidated_weather().get(0).getPredictability()));

                generateView(montreal.getConsolidated_weather(), getView());
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
        recyclerView = view.findViewById(R.id.montrealRecyclerView);
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
            b.putParcelable("weather", montreal.getConsolidated_weather().get(position));
            b.putString("city","Montreal");
            PredictionFragment fragment = new PredictionFragment();
            fragment.setArguments(b);

            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
    };
}