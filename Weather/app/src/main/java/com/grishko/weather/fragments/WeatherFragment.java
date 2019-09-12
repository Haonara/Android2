package com.grishko.weather.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.grishko.weather.R;
import com.grishko.weather.activities.MainActivity;
import com.grishko.weather.model.Parceling;
import com.grishko.weather.model.WeatherService;

import java.util.Objects;

import static com.grishko.weather.fragments.SettingsFragment.STATE;

public class WeatherFragment extends Fragment {

    public static final String TAG="WeatherFragment";
    private SensorManager sensorManager;
    private Sensor sensorTemperature;
    private Sensor sensorHumidity;
    private boolean binded=false;
    private WeatherService weatherService;
    private TextView status;
    private float temp;
    private float hum;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_fragment, container,false);
    }

    ServiceConnection weatherServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            WeatherService.LocalWeatherBinder binder = (WeatherService.LocalWeatherBinder) service;
            weatherService = binder.getService();
            binded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binded = false;
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView cityName=view.findViewById(R.id.textView_city_name);
        TextView temperature=view.findViewById(R.id.textView_temperature);
        TextView wet=view.findViewById(R.id.textView_wet);
        TextView wind=view.findViewById(R.id.textView_wind);
        status=view.findViewById(R.id.textView_status);
        Button settings=view.findViewById(R.id.button_settings);
        Button story=view.findViewById(R.id.button_story);

        sensorManager= (SensorManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager != null){
            sensorTemperature=sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            sensorHumidity=sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        }

        if(getArguments()!= null){

            Parceling parceling=(Parceling) getArguments().getSerializable(STATE);

            if(parceling != null){
                cityName.setText(parceling.getCityName());
                String textTemp=getString(R.string.temperature)+temp;
                temperature.setText(textTemp);

                if (parceling.isVisibilityWet()){
                    String textWet=getString(R.string.wet)+hum;
                    wet.setText(textWet);
                    wet.setVisibility(View.VISIBLE);
                }else{
                    wet.setVisibility(View.INVISIBLE);
                }

                if (parceling.isVisibilityWind()){
                    wind.setVisibility(View.VISIBLE);
                }else{
                    wind.setVisibility(View.INVISIBLE);
                }
            }
        }

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity()!= null){
                    ((MainActivity)getActivity()).openFragment(SettingsFragment.TAG, null);
                }
            }
        });

        story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity()!= null){
                    ((MainActivity)getActivity()).openFragment(StoryFragment.TAG, null);
                }
            }
        });

        Intent intent=new Intent(getActivity(),WeatherService.class);
        getActivity().bindService(intent, weatherServiceConnection, Context.BIND_AUTO_CREATE);
        //startWeather(intent);
        //String weather= weatherService.getWeather();
        //status.setText(weather);

    }

    private final SensorEventListener listenerTemperature = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            temp=event.values[0];
        }
    };

    private final SensorEventListener listenerHumidity = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            hum=event.values[0];
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (sensorManager != null) {
            sensorManager.registerListener(listenerTemperature,sensorTemperature,SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(listenerHumidity,sensorHumidity,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerTemperature,sensorTemperature);
        sensorManager.unregisterListener(listenerHumidity,sensorHumidity);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (binded) {
            // Unbind Service
            Objects.requireNonNull(getActivity()).unbindService(weatherServiceConnection);
            //binded = false;
        }
    }

    public void startWeather (Intent intent){
        Objects.requireNonNull(getActivity()).startService(intent);
        String weather= weatherService.getWeather();
        status.setText(weather);
    }
}
