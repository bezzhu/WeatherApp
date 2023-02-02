package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.example.weatherapp.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    CurrentWeather currentWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil
                .setContentView(MainActivity.this,R.layout.activity_main);


        String apiKey = "cf579f898ed755c768312ad882e1ba4f";

        double latitude = 41.716668;
        double longitude = 44.783333;
        String forecastUrl = "https://api.openweathermap.org/data/2.5/weather?lat="
                 + latitude+"&lon=" + longitude + "&appid=" + apiKey;

    if(isNetworkAvailable()) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(forecastUrl)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
    
            }
    
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {

                try {
                    String jsonData = Objects.requireNonNull(response.body()).string();
                    Log.v(TAG, jsonData);
                    if (response.isSuccessful()) {
                        currentWeather = getCurrentDetails(jsonData);

                        CurrentWeather displayWeather = new CurrentWeather(currentWeather.getCountry(),
                                currentWeather.getName() , currentWeather.getTemp());

                        binding.setDisplayWeather(displayWeather);

                    } else {
                        alertUserAboutError();
                    }
    
                } catch (IOException ex) {
                    Log.e(TAG, "IoException" , ex);
                }
                catch (JSONException ex){
                    Log.e(TAG, "JsonException", ex);
                }
            }
        });
    }
    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);

        CurrentWeather currentWeather = new CurrentWeather();

        currentWeather.setName(forecast.getString("name"));
        currentWeather.setCountry(forecast.getJSONObject("sys").getString("country"));
        currentWeather.setTemp(forecast.getJSONObject("main").getDouble("temp"));
        currentWeather.setFeelsLike(forecast.getJSONObject("main").getDouble("feels_like"));
        currentWeather.setHumidity(forecast.getJSONObject("main").getDouble("humidity"));
        currentWeather.setTime(forecast.getLong("timezone"));

        return currentWeather;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;

        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }else{
             Toast.makeText(this, R.string.Internet_error_text , Toast.LENGTH_LONG).show();
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialogFragment = new AlertDialogFragment();
        dialogFragment.show(getFragmentManager(), "error");
    }
}