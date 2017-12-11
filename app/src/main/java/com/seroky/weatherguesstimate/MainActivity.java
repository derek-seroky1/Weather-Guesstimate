package com.seroky.weatherguesstimate;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;

import com.felipecsl.gifimageview.library.GifImageView;
import com.seroky.weatherguesstimate.providers.CitiesProvider;
import com.seroky.weatherguesstimate.viewmodels.MainViewModel;
import com.seroky.weatherguesstimate.views.CityListViewActivity;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class should display the current weather for the current location (default= New York City)
 * This view should display the 5 day forecast
 */
public class MainActivity extends AppCompatActivity
{

    GifImageView gifView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the cities provider in the background for quicker search
        CitiesProvider.getInstance(getApplicationContext());

        new CountDownTimer(5000, 1000) {

            /**
             * Callback fired on regular interval.
             *
             * @param millisUntilFinished The amount of time until finished.
             */
            @Override
            public void onTick(long millisUntilFinished)
            {

            }

            /**
             * Callback fired when the time is up.
             */
            @Override
            public void onFinish()
            {
                openCities();
            }
        }.start();

        MainViewModel mainViewModel = new MainViewModel(getApplicationContext());

        InputStream is = null;
        byte[] bytes;
        try
        {
            is = getAssets().open("weather_200w_d.gif");
            bytes = new byte[is.available()];
            is.read(bytes);
            is.close();
            gifView = findViewById(R.id.gif_image_view);
//        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.weather_200w_d);
//        bitmap
            gifView.setBytes(bytes);
        } catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    @Override
    protected void onStart()
    {
        super.onStart();
        gifView.startAnimation();
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

    public void openCities()
    {
        startActivity(new Intent(this, CityListViewActivity.class));
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        gifView.stopAnimation();
    }
}
