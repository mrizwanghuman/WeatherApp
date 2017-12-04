package com.riz.admin.weatherapp;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.riz.admin.weatherapp.retrofithelper.RetrofitAdapter;
import com.riz.admin.weatherapp.retrofithelper.RetrofitDayHelper;
import com.riz.admin.weatherapp.weatherdata.DisplayLocation;
import com.riz.admin.weatherapp.weatherdata.WeatherData;
import com.riz.admin.weatherapp.weatherdata.daily.DailyWeatherData;
import com.riz.admin.weatherapp.weatherdata.daily.FCTTIME;
import com.riz.admin.weatherapp.weatherdata.daily.HourlyForecast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends AppCompatActivity {
RecycleViewAdapterTest adapter;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewSec;
    private Toolbar myToolbar;
    private TextView tvMenuBarCity;
    private TextView tvMenuTemp;
    private TextView tvMenuCond;
    private EditText etZipCode;
    private Button btnsearchSip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

         //...
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tvMenuBarCity = findViewById(R.id.tvMenuCityName);
        tvMenuTemp = findViewById(R.id.tvMenuTemp);
        tvMenuCond = findViewById(R.id.tvMenuCond);
        toolbarMenuSetup();



    }

    private void toolbarMenuSetup() {
        myToolbar = findViewById(R.id.main_app_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.app_name);
        etZipCode = findViewById(R.id.etSeachZipCode);
        btnsearchSip = findViewById(R.id.btnSearchZip);

        btnsearchSip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String zipCode= etZipCode.getText().toString();
                Log.d("TAG", "onClick: "+zipCode);
                getTempMainBar(zipCode);

                getDailyTemp(zipCode);
            }
        });

    }

    private void getTempMainBar(String zipCode) {
        RetrofitAdapter.weatherDataCall(zipCode+".json").enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                Log.d("Tag", "onResponse: "+response.body().getCurrentObservation().getDisplayLocation().getCity());
                DisplayLocation getLocation = response.body().getCurrentObservation().getDisplayLocation();
                String getWeather = response.body().getCurrentObservation().getWeather();
                Double getTemp = response.body().getCurrentObservation().getTempF();
                Integer geTempInt = getTemp.intValue();
                tvMenuBarCity.setText(getLocation.getCity()+","+getLocation.getState());
                tvMenuTemp.setText(geTempInt.toString());
                tvMenuCond.setText(getWeather);
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.d("tag", "onFailure: "+t.getMessage());

            }
        });
    }

//    private void weahterApiCont(String zipCode) {
//
//
//        RetrofitAdapter.weatherDataCall("/conditions/q/"+zipCode+".json").enqueue(new Callback<WeatherData>() {
//            @Override
//            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
//                Log.d("Tag", "onResponse: "+response.body().getCurrentObservation().getDisplayLocation().getCity());
//                DisplayLocation getLocation = response.body().getCurrentObservation().getDisplayLocation();
//                String getWeather = response.body().getCurrentObservation().getWeather();
//                Double getTemp = response.body().getCurrentObservation().getTempF();
//                Integer geTempInt = getTemp.intValue();
//                tvMenuBarCity.setText(getLocation.getCity()+","+getLocation.getState());
//                tvMenuTemp.setText(geTempInt.toString());
//                tvMenuCond.setText(getWeather);
//            }
//
//            @Override
//            public void onFailure(Call<WeatherData> call, Throwable t) {
//                Log.d("tag", "onFailure: "+t.getMessage());
//
//            }
//        });
//    }

    private void getDailyTemp(String zipcode) {
        // data to populate the RecyclerView with
final List<HourlyForecast> hourlyForecasts = new ArrayList<>();
final List<FCTTIME> fcttimes = new ArrayList<>();
final List<HourlyForecast> hourlyForecastsTomorw = new ArrayList<>();
final List<FCTTIME> fcttimesTomorw = new ArrayList<>();

final List<HourlyForecast> hourlyForecaststwo = new ArrayList<>();
        // set up the RecyclerView
        recyclerView = findViewById(R.id.rcToday);
        recyclerViewSec = findViewById(R.id.rcTomorow);
        int numberOfColumns = 4;
        recyclerViewSec.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        RetrofitDayHelper.getDailyWeather(zipcode+".json").enqueue(new Callback<DailyWeatherData>() {
            @Override
            public void onResponse(Call<DailyWeatherData> call, Response<DailyWeatherData> response) {

                hourlyForecasts.addAll(response.body().getHourlyForecast());

                for (int i = 0; i < hourlyForecasts.size(); i++) {

                    fcttimes.add(hourlyForecasts.get(i).getFCTTIME());


                }
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("d");
                String todayDate = df.format(c.getTime());
                int todaydateInt = Integer.parseInt(todayDate);
                List<FCTTIME>fcttimestwo = new ArrayList<>();
                for (int j = 0; j < fcttimes.size(); j++) {
                    String todayString = fcttimes.get(j).getMday();
                    int todayInt = Integer.parseInt(todayString);

                    Log.d("Calender", "onResponse: "+todayInt);
                    Log.d("FC", "onResponse: "+todaydateInt);
                    if (todayInt <= todaydateInt) {
                        Log.d("Ifstatement", "onResponse: ");
                        fcttimestwo.add(fcttimes.get(j));
                        hourlyForecaststwo.add(hourlyForecasts.get(j));

                    }else{
                        fcttimesTomorw.add(fcttimes.get(j));
                        hourlyForecastsTomorw.add(hourlyForecasts.get(j));
                    }
                    Log.d("fcttimesTomorw", "onResponse: "+fcttimesTomorw.size());

                }

                Log.d("Two", "onResponse: "+fcttimestwo.size());

                RecycleViewAdapterTest recycleViewAdapterTest = new RecycleViewAdapterTest(hourlyForecaststwo, fcttimestwo, Home.this);
                        recyclerView.setAdapter(recycleViewAdapterTest);
                RecycleViewAdapterTest recycleViewAdapter = new RecycleViewAdapterTest(hourlyForecastsTomorw, fcttimesTomorw, Home.this);
                recyclerViewSec.setAdapter(recycleViewAdapter);
            }

            @Override
            public void onFailure(Call<DailyWeatherData> call, Throwable t) {
                Log.d("onFailure", "onFailure: "+t.getMessage());

            }
        });
        adapter = new RecycleViewAdapterTest( hourlyForecasts, fcttimes, this);

        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
