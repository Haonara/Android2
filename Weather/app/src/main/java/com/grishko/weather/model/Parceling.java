package com.grishko.weather.model;

import java.io.Serializable;

public class Parceling implements Serializable{
    private String cityName;
    private boolean visibilityWet;
    private boolean visibilityWind;

    public Parceling(String cityName, boolean visibilityWet, boolean visibilityWind) {
        this.cityName = cityName;
        this.visibilityWet = visibilityWet;
        this.visibilityWind = visibilityWind;
    }

    public String getCityName() {
        return cityName;
    }

    public boolean isVisibilityWet() {
        return visibilityWet;
    }

    public boolean isVisibilityWind() {
        return visibilityWind;
    }

}
