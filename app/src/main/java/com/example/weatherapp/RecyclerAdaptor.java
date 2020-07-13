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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

        holder.maxTemp.setText(String.format("%.2f",currentWeather.getMax_temp()).trim());
        holder.minTemp.setText(String.format("%.2f",currentWeather.getMin_temp()).trim());
        holder.dayOfWeek.setText(dayOfWeek(currentWeather.getApplicable_date()));
        Picasso.get().load("https://www.metaweather.com/static/img/weather/ico/" + currentWeather.getWeather_state_abbr() + ".ico").into(holder.weatherIcon);


    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        clickListener = onClickListener;
    }

    public String dayOfWeek(String date) {
        String day = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        day = new SimpleDateFormat("EEEE").format(date1);
        day +=", "+ new SimpleDateFormat("d").format(date1);
        return day;
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
