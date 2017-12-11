package com.seroky.weatherguesstimate;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.seroky.weatherguesstimate.providers.CitiesProvider;
import com.seroky.weatherguesstimate.viewmodels.MainViewModel;
import com.seroky.weatherguesstimate.views.CityListViewActivity;

/**
 * This class should display the current weather for the current location (default= New York City)
 * This view should display the 5 day forecast
 */
public class MainActivity extends AppCompatActivity
{



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Initialize the cities provider in the background for quicker search
        CitiesProvider.getInstance(getApplicationContext());


        MainViewModel mainViewModel = new MainViewModel(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView    searchView    = (SearchView) menu.findItem(R.id.search).getActionView();
        // Assumes current activity is the searchable activity
        if (searchManager != null)
        {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default

        return true;
    }

    public void openCities(View view)
    {
        startActivity(new Intent(this, CityListViewActivity.class));
    }
}
