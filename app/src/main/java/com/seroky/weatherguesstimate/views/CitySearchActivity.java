package com.seroky.weatherguesstimate.views;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.seroky.weatherguesstimate.R;
import com.seroky.weatherguesstimate.models.City;
import com.seroky.weatherguesstimate.providers.CitiesProvider;
import com.seroky.weatherguesstimate.viewmodels.CitySearchViewModel;

import java.util.ArrayList;

/**
 * This class assists with searching the city file to determine the cityId to query the server.
 */
public class CitySearchActivity extends AppCompatActivity
{

    CitiesProvider mCitiesProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);
        mCitiesProvider = CitiesProvider.getInstance(getApplicationContext());

        // Get the intent, verify the action and get the query
        handleIntent( getIntent() );
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String              query               = intent.getStringExtra(SearchManager.QUERY);

            // Let's get the matches
            ArrayList<City>     temp                = mCitiesProvider.getCity(query);
            City[]              matches             = new City[temp.size()];
            temp.toArray(matches); //convert that into an array to use with ArrayAdapter in view model

            CitySearchViewModel citySearchViewModel =
                    new CitySearchViewModel(getApplicationContext(), matches);

            ListView            listView            = findViewById(R.id.city_search_listview);
            listView.setAdapter(citySearchViewModel);
        }
    }
}
