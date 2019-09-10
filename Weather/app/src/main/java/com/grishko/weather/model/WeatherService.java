package com.grishko.weather.model;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class WeatherService extends Service {

    public WeatherService() {
    }

    private final IBinder binder = new LocalWeatherBinder();

    public class LocalWeatherBinder extends Binder {

        public WeatherService getService()  {
            return WeatherService.this;
        }
    }

    @Override
    public void onCreate() {
        // Служба создается
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Служба стартовала
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // Удаление привязки
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        // Перепривязка клиента
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        // Уничтожение службы
        super.onDestroy();
    }

    public String getWeather (){
        String weather;
        String[] weathers = new String[]{"Rainy", "Hot", "Cool", "Warm" ,"Snowy"};

        int i= new Random().nextInt(5);

        weather =weathers[i];

        return  weather;
    }

}
