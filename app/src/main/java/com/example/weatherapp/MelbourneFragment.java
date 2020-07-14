package com.example.weatherapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

public class MelbourneFragment extends Fragment {
    TextView melbourneHeaderText, melbourneWeatherStateName, melbourneDayOfWeek, melbourneMaximumTemperature, melbourneActualTemperature, melbourneMinimumTemperature, melbourneHumidity, melbournePredictability;
    ImageView todayWeatherIcon;
    private RecyclerView recyclerView;
    RecyclerAdaptor recyclerAdaptor;
    CityInfo melbourne;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_melbourne, container, false);
        retroFitMelbourne();
        return view;
    }

    public void retroFitMelbourne() {

        Api api = RetrofitInstance.getRetrofit().create(Api.class);
        final Call<CityInfo> callMelbourne = api.getMelbourneDetails();
        callMelbourne.enqueue(new Callback<CityInfo>() {
            @Override
            public void onResponse(Call<CityInfo> call, Response<CityInfo> response) {
                melbourne = response.body();

                setTextView(melbourneHeaderText, R.id.melbourneHead, melbourne.getTitle());
                setImageView(todayWeatherIcon, R.id.melbourneWeatherIcon, melbourne.getConsolidated_weather().get(0).getWeather_state_abbr());
                setTextView(melbourneDayOfWeek, R.id.melbourneDayOfWeek, WeatherSupport.dayOfWeek(melbourne.getConsolidated_weather().get(0).getApplicable_date()));
                setTextView(melbourneWeatherStateName, R.id.melbourneWeatherState, melbourne.getConsolidated_weather().get(0).getWeather_state_name());
                setTextView(melbourneMaximumTemperature, R.id.melbourneMaxTemp, WeatherSupport.convertDouble(melbourne.getConsolidated_weather().get(0).getMax_temp()));
                setTextView(melbourneActualTemperature, R.id.melbourneActualTemp, WeatherSupport.convertDouble(melbourne.getConsolidated_weather().get(0).getThe_temp()));
                setTextView(melbourneMinimumTemperature, R.id.melbourneMinTemp, WeatherSupport.convertDouble(melbourne.getConsolidated_weather().get(0).getMin_temp()));
                setTextView(melbourneHumidity, R.id.melbourneHumidity, WeatherSupport.convertInt(melbourne.getConsolidated_weather().get(0).getHumidity()));
                setTextView(melbournePredictability, R.id.melbournePredictability, WeatherSupport.convertInt(melbourne.getConsolidated_weather().get(0).getPredictability()));

                generateView(melbourne.getConsolidated_weather(), getView());
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
        recyclerView = view.findViewById(R.id.melbourneRecyclerView);
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
            b.putParcelable("weather", melbourne.getConsolidated_weather().get(position));
            b.putString("city","Melbourne");
            PredictionFragment fragment = new PredictionFragment();
            fragment.setArguments(b);

            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
    };
}