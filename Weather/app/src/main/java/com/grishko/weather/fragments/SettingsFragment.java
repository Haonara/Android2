package com.grishko.weather.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.grishko.weather.R;
import com.grishko.weather.activities.MainActivity;
import com.grishko.weather.model.CityIndexParcel;
import com.grishko.weather.model.Parceling;

import java.util.Objects;

import static com.grishko.weather.fragments.CitiesListFragment.INDEX;


public class SettingsFragment extends Fragment {

    public static final String TAG="SettingsFragment";
    public static final String APP_SAVES = "MySettings";
    public static final String APP_SAVES_CITY = "CITY";
    public static final String APP_SAVES_WET = "CheckboxWet";
    public static final String APP_SAVES_WIND = "CheckboxWind";
    public static final String STATE="STATE";

    private Parceling parceling;
    private EditText enterCity;
    private CheckBox wet;
    private CheckBox wind;
    private Button ok;
    private String enteredCity;
    SharedPreferences mySettings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container,false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);

        mySettings = Objects.requireNonNull(getActivity()).getSharedPreferences(APP_SAVES, Context.MODE_PRIVATE);
        enterCity =view.findViewById(R.id.editText_city_enter);
        Button cities=view.findViewById(R.id.button_back);
        wind=view.findViewById(R.id.checkBox_wind);
        ok=view.findViewById(R.id.button_ok);
        wet=view.findViewById(R.id.checkBox_wet);

        if(mySettings.contains(APP_SAVES_CITY)) {
            enterCity.setText(mySettings.getString(APP_SAVES_CITY, "Vladivostok"));
        }
        if (mySettings.contains(APP_SAVES_WET)){
            wet.setChecked(mySettings.getBoolean(APP_SAVES_WET,false));
        }
        if (mySettings.contains(APP_SAVES_WIND)){
            wind.setChecked(mySettings.getBoolean(APP_SAVES_WIND, false));
        }

        if(getArguments()!= null){
            CityIndexParcel cityIndexParcel=(CityIndexParcel)getArguments().getSerializable(INDEX);
            if (cityIndexParcel != null){
                enterCity.setText(cityIndexParcel.getCityName());
            }
        }

        wet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (wet.isChecked()){
                    SharedPreferences.Editor editor=mySettings.edit();
                    editor.putBoolean(APP_SAVES_WET, true);
                    editor.apply();
                } else {
                    SharedPreferences.Editor editor=mySettings.edit();
                    editor.putBoolean(APP_SAVES_WET, false);
                    editor.apply();
                }
            }
        });

        wind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (wind.isChecked()){
                    SharedPreferences.Editor editor=mySettings.edit();
                    editor.putBoolean(APP_SAVES_WIND, true);
                    editor.apply();
                }else {
                    SharedPreferences.Editor editor=mySettings.edit();
                    editor.putBoolean(APP_SAVES_WIND, false);
                    editor.apply();
                }
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enteredCity= enterCity.getText().toString();

                if(enteredCity.length()<1){
                    Toast.makeText(getActivity(),"Wrong city name", Toast.LENGTH_SHORT).show();
                }else{
                    parceling=new Parceling(enterCity.getText().toString(), wet.isChecked(), wind.isChecked());

                    Bundle bundle=new Bundle();
                    bundle.putSerializable(STATE,parceling);

                    if (getActivity()!= null){
                        ((MainActivity)getActivity()).openFragment(WeatherFragment.TAG, bundle);
                    }
                }
            }
        });

        cities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity()!= null){
                    ((MainActivity)getActivity()).openFragment(CitiesListFragment.TAG, null);
                }
            }
        });

    }


    @Override
    public void onDestroyView() {
        ok.setOnClickListener(null);
        SharedPreferences.Editor editor=mySettings.edit();
        editor.putString(APP_SAVES_CITY, enteredCity);
        editor.apply();
        super.onDestroyView();
    }
}
