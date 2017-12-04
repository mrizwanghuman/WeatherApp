package com.riz.admin.weatherapp.retrofithelper;
import com.riz.admin.weatherapp.weatherdata.WeatherData;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by  Admin on 12/3/2017.
 */

public class RetrofitAdapter {
    private static  String BASE_URL="http://api.wunderground.com/api/";
    private static  String apiKey="658671bf6a896877/";



    public static Retrofit retrofitConfig(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL+apiKey).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }
    public static Call<WeatherData> weatherDataCall(String zipCode){
        Retrofit retrofit = retrofitConfig();
        WeatherDataService weatherDataService = retrofit.create(WeatherDataService.class);
        return weatherDataService.getWeatherData(zipCode);
    }
}

