package com.grishko.weather.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grishko.weather.R;
import com.grishko.weather.model.DataAdapter;
import com.grishko.weather.model.TemperatureStory;

import java.util.ArrayList;
import java.util.List;

public class StoryFragment extends Fragment {

    public static final String TAG="StoryFragment";

    List <TemperatureStory> temperatureStoryList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.story_fragment, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        setInitialData();
        RecyclerView recyclerView=view.findViewById(R.id.list);
        DataAdapter adapter=new DataAdapter(getContext(),temperatureStoryList);
        recyclerView.setAdapter(adapter);

    }

    private void setInitialData(){
        temperatureStoryList.add(new TemperatureStory("Moscow", "temperature=15"));
    }
}
