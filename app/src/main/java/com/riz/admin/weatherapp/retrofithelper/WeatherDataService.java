package com.riz.admin.weatherapp.retrofithelper;

import com.riz.admin.weatherapp.weatherdata.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by  Admin on 12/3/2017.
 */

public interface WeatherDataService {
    @GET("conditions/q/{zipCode}")
    Call<WeatherData> getWeatherData(@Path(value = "zipCode", encoded = true) String zipCode);
}
