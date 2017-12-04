package com.riz.admin.weatherapp;

import android.content.Context;
import android.icu.text.DateFormat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.riz.admin.weatherapp.weatherdata.daily.FCTTIME;
import com.riz.admin.weatherapp.weatherdata.daily.HourlyForecast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by  Admin on 12/3/2017.
 */

public class RecycleViewAdapterTest extends RecyclerView.Adapter<RecycleViewAdapterTest.ViewHolder> {
    //private String[] mData = new String[0];
    private List<HourlyForecast> hourlyForecasts;
    private List<FCTTIME> fcttimes;
Context context;
    private int todayInt;
    private int todaydateInt;

//    public RecycleViewAdapterTest(Context context, String[] data) {
//        this.mData = data;
//
//        this.mInflater = LayoutInflater.from(context);
//    }

    public RecycleViewAdapterTest(List<HourlyForecast> hourlyForecasts, List<FCTTIME> fcttimes, Context context) {
        this.hourlyForecasts = hourlyForecasts;
        this.fcttimes = fcttimes;
        this.mInflater = LayoutInflater.from(context);

    }

    private LayoutInflater mInflater;


    @Override
    public RecycleViewAdapterTest.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.grid_cell_layout, parent, false);
        context= parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycleViewAdapterTest.ViewHolder holder, int position) {


        String hour = fcttimes.get(position).getCivil();
        String weatherIcon = hourlyForecasts.get(position).getIconUrl();
        String dayTemp= hourlyForecasts.get(position).getTemp().getEnglish();
        // Log.d("TAG", "onCreate: "+formattedDate);
        //String animal = mData[position];

            Glide.with(context).load(weatherIcon).into(holder.tvIcon);
            holder.tvHourDis.setText(hour);
            holder.tvTempDis.setText(dayTemp);


    }

    @Override
    public int getItemCount() {
        return hourlyForecasts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHourDis;
         ImageView tvIcon;
        TextView tvTempDis;

        public ViewHolder(View itemView) {
            super(itemView);


                tvHourDis = itemView.findViewById(R.id.tv_hour_dis);
                tvIcon = itemView.findViewById(R.id.tv_icon_dis);
                tvTempDis = itemView.findViewById(R.id.tv_temp_dis);


        }
    }



}
