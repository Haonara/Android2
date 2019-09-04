package com.grishko.weather.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.grishko.weather.R;
import com.grishko.weather.activities.MainActivity;
import com.grishko.weather.model.CityIndexParcel;
import com.grishko.weather.model.Parceling;

import static com.grishko.weather.fragments.CitiesListFragment.INDEX;


public class SettingsFragment extends Fragment {

    public static final String TAG="SettingsFragment";

    private Parceling parceling;
    public static final String STATE="STATE";
    private EditText enter_city;
    private CheckBox wet;
    private CheckBox wind;
    private Button ok;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);

        enter_city=view.findViewById(R.id.editText_city_enter);
        Button cities=view.findViewById(R.id.button_back);
        wind=view.findViewById(R.id.checkBox_wind);
        ok=view.findViewById(R.id.button_ok);
        wet=view.findViewById(R.id.checkBox_wet);



        if(getArguments()!=null){
            CityIndexParcel cityIndexParcel=(CityIndexParcel)getArguments().getSerializable(INDEX);
            if (cityIndexParcel!=null){
                enter_city.setText(cityIndexParcel.getCityName());
            }
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String enteredCity=enter_city.getText().toString();

                if(enteredCity.length()<1){
                    Toast.makeText(getActivity(),"Wrong city name", Toast.LENGTH_SHORT).show();
                }else{
                    parceling=new Parceling(enter_city.getText().toString(), wet.isChecked(), wind.isChecked());

                    Bundle bundle=new Bundle();
                    bundle.putSerializable(STATE,parceling);

                    if (getActivity()!=null){
                        ((MainActivity)getActivity()).openFragment(WeatherFragment.TAG, bundle);
                    }
                }
            }
        });

        cities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity()!=null){
                    ((MainActivity)getActivity()).openFragment(CitiesListFragment.TAG, null);
                }
            }
        });

    }


    @Override
    public void onDestroyView() {
        ok.setOnClickListener(null);
        super.onDestroyView();
    }
}
