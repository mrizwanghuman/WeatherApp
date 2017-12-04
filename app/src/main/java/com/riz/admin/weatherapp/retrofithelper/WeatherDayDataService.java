package com.riz.admin.weatherapp.retrofithelper;

import com.riz.admin.weatherapp.weatherdata.daily.DailyWeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by  Admin on 12/3/2017.
 */

public interface WeatherDayDataService {
    @GET("hourly/q/{zipCode}")
    Call<DailyWeatherData> getDailyWeather(@Path(value = "zipCode", encoded = true) String zipCode);
}
