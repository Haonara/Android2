package com.grishko.weather.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
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

import java.util.Objects;

import static com.grishko.weather.fragments.SettingsFragment.STATE;

public class WeatherFragment extends Fragment {

    public static final String TAG="WeatherFragment";
    private SensorManager sensorManager;
    private Sensor sensorHumidity;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView cityName=view.findViewById(R.id.textView_city_name);
        TextView wet=view.findViewById(R.id.textView_wet);
        TextView wind=view.findViewById(R.id.textView_wind);
        Button settings=view.findViewById(R.id.button_settings);
        Button story=view.findViewById(R.id.button_story);

        sensorManager= (SensorManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager!=null){
            sensorHumidity=sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        }

        if(getArguments()!=null){

            Parceling parceling=(Parceling) getArguments().getSerializable(STATE);

            if(parceling!=null){
                cityName.setText(parceling.getCityName());

                if (parceling.isVisibilityWet()){
                    wet.setText("");
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
                if (getActivity()!=null){
                    ((MainActivity)getActivity()).openFragment(SettingsFragment.TAG, null);
                }
            }
        });

        story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity()!=null){
                    ((MainActivity)getActivity()).openFragment(StoryFragment.TAG, null);
                }
            }
        });

    }
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
            sensorManager.registerListener(listenerHumidity,sensorHumidity,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerHumidity,sensorHumidity);
    }
}
