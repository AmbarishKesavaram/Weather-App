package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.ViewHolder> {

    private ArrayList<WeatherInfo> alWeather;
    private Context context;
    private View.OnClickListener clickListener;

    public RecyclerAdaptor(ArrayList<WeatherInfo> alWeather, Context context) {
        this.alWeather = alWeather;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        WeatherInfo currentWeather = alWeather.get(position);

        holder.maxTemp.setText(WeatherSupport.convertDouble(currentWeather.getMax_temp()));
        holder.minTemp.setText(WeatherSupport.convertDouble(currentWeather.getMin_temp()));
        holder.dayOfWeek.setText(WeatherSupport.dayOfWeek(currentWeather.getApplicable_date()));
        Picasso.get().load("https://www.metaweather.com/static/img/weather/ico/" + currentWeather.getWeather_state_abbr() + ".ico").into(holder.weatherIcon);


    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        clickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return alWeather.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView weatherIcon;
        TextView dayOfWeek, maxTemp, minTemp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            weatherIcon = itemView.findViewById(R.id.weatherIcon);
            dayOfWeek = itemView.findViewById(R.id.dayOfweek);
            maxTemp = itemView.findViewById(R.id.maxTemp);
            minTemp = itemView.findViewById(R.id.minTemp);

            itemView.setTag(this);
            itemView.setOnClickListener(clickListener);
        }
    }
}
