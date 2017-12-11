package com.seroky.weatherguesstimate.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.seroky.weatherguesstimate.R;
import com.seroky.weatherguesstimate.viewmodels.CitiesViewModel;

/**
 * This activity displays all of the saved cities
 */
public class CityListViewActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list_view);

        RecyclerView recyclerView    = findViewById(R.id.citiesRecyclerView);
        CitiesViewModel      citiesViewModel = new CitiesViewModel(getApplicationContext());
        recyclerView.setAdapter(citiesViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
