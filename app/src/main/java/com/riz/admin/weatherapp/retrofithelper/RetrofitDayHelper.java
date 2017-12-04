package com.riz.admin.weatherapp.retrofithelper;

import com.riz.admin.weatherapp.weatherdata.daily.DailyWeatherData;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by  Admin on 12/3/2017.
 */

public class RetrofitDayHelper {
    public static final String DAY_BASE_URL ="http://api.wunderground.com/api/658671bf6a896877/";
    private static Retrofit RetrofitDayHelper(){
        return new Retrofit.Builder().baseUrl(DAY_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

    }
    public static Call<DailyWeatherData> getDailyWeather(String zipCode){
        Retrofit retrofit = RetrofitDayHelper();
        WeatherDayDataService weatherDayDataService = retrofit.create(WeatherDayDataService.class);
        return weatherDayDataService.getDailyWeather(zipCode);

    }
}
