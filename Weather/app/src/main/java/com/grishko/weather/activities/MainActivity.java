package com.grishko.weather.activities;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.grishko.weather.R;
import com.grishko.weather.fragments.CitiesListFragment;
import com.grishko.weather.fragments.SettingsFragment;
import com.grishko.weather.fragments.StoryFragment;
import com.grishko.weather.fragments.WeatherFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState==null){
            openFragment(SettingsFragment.TAG, null);
        }

    }

    public void openFragment(@Nullable String fragmentTag, @Nullable Bundle args){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        Fragment fragmentToOpen;

        if (fragmentTag==null){
            fragmentTag=SettingsFragment.TAG;
        }

        switch(fragmentTag){
            default:
                case SettingsFragment.TAG:
                fragmentToOpen=new SettingsFragment();
                break;
            case CitiesListFragment.TAG:
                fragmentToOpen= new CitiesListFragment();
                break;
            case WeatherFragment.TAG:
                fragmentToOpen=new WeatherFragment();
                break;
            case StoryFragment.TAG:
                fragmentToOpen=new StoryFragment();
                break;
        }

        if(args!=null){
            fragmentToOpen.setArguments(args);
        }

        fragmentTransaction
                .replace(R.id.my_container,fragmentToOpen)
                .addToBackStack(fragmentTag)
                .commit();
    }

}
