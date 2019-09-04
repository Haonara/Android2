package com.grishko.weather.model;

public class TemperatureStory {

    public String cityName;
    public String temperature;

    public TemperatureStory(String cityName, String temperature) {
        this.cityName = cityName;
        this.temperature = temperature;
    }

    public String getCityName() {
        return cityName;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
