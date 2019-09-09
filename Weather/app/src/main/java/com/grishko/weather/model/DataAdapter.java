package com.grishko.weather.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grishko.weather.R;
import com.grishko.weather.fragments.StoryFragment;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<TemperatureStory> temperatureStoryList;

    public DataAdapter(Context context, List<TemperatureStory> temperatureStoryList) {
        this.inflater = LayoutInflater.from(context);
        this.temperatureStoryList = temperatureStoryList;
    }

    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        TemperatureStory temperatureStory=temperatureStoryList.get(position);
        viewHolder.nameView.setText(temperatureStory.getCityName());
        viewHolder.tempView.setText(temperatureStory.getTemperature());
    }

    @Override
    public int getItemCount() {
        return temperatureStoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;
        final TextView tempView;
        ViewHolder(View view){
            super(view);
            nameView=view.findViewById(R.id.name);
            tempView=view.findViewById(R.id.temp);

        }
    }
}
