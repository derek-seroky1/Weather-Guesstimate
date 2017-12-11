package com.seroky.weatherguesstimate.views;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.seroky.weatherguesstimate.R;
import com.seroky.weatherguesstimate.models.city.City;
import com.seroky.weatherguesstimate.viewmodels.CitiesViewModel;

/**
 * This activity displays all of the saved cities
 */
public class CityListViewActivity extends AppCompatActivity
{
    private ProgressDialog mProgressDialog;
    private CitiesViewModel mCitiesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list_view);
        RecyclerView recyclerView    = findViewById(R.id.citiesRecyclerView);
        recyclerView.setVisibility(View.GONE);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Intent intent = getIntent();
        if (intent.hasExtra("city"))
        {
            mCitiesViewModel =
                    new CitiesViewModel(getApplicationContext(), recyclerView,
                                        (City) intent.getParcelableExtra("city"));
            myToolbar.setTitle(mCitiesViewModel.mCurrentCity.getName());
        }
        else
        {
            mCitiesViewModel = new CitiesViewModel(getApplicationContext(), recyclerView);
        }
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
}
