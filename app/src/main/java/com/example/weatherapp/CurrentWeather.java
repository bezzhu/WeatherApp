package com.example.weatherapp;

public class CurrentWeather {

    private String country;
    private String name;
    private String weather;
    private Long time;
    private double temp;
    private double feelsLike;
    private double humidity;


    public CurrentWeather() {
    }

    public CurrentWeather(String country, String name, double temp) {
        this.country = country;
        this.name = name;
        this.temp = temp;
    }

    public CurrentWeather(String country, String name, String weather, Long time,
                          double temp, double feelsLike, double humidity) {
        this.country = country;
        this.name = name;
        this.weather = weather;
        this.time = time;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
