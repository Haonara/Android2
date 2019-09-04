package com.grishko.weather.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.grishko.weather.R;
import com.grishko.weather.activities.MainActivity;
import com.grishko.weather.model.CityIndexParcel;

public class CitiesListFragment extends ListFragment {

    public static final String TAG="CitiesListFragment";
    public static final String INDEX="Index";
    CityIndexParcel cityIndexParcel;
    int currentPosition = 0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activityContext = getActivity();
        if (activityContext == null) {
            return;
        }
        createAndSetAdapter(activityContext);

    }

    private void createAndSetAdapter(@NonNull Context context) {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                context,
                R.array.cities_list,
                android.R.layout.simple_list_item_activated_1);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        currentPosition = position;
        TextView text=(TextView)v;
        String itemText=text.getText().toString();

        cityIndexParcel=new CityIndexParcel(currentPosition,itemText);
        Bundle bundle= new Bundle();
        bundle.putSerializable(INDEX,cityIndexParcel);

        if (getActivity()!=null){
            ((MainActivity)getActivity()).openFragment(SettingsFragment.TAG, bundle);
        }

    }





}
