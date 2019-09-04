package com.grishko.weather.model;

import java.io.Serializable;

public class CityIndexParcel implements Serializable {

    private int index;
    private String cityName;

    public CityIndexParcel(int index, String cityName) {
        this.index = index;
        this.cityName = cityName;
    }

    public int getIndex() {
        return index;
    }

    public String getCityName() {
        return cityName;
    }

}
