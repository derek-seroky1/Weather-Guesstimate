package com.seroky.weatherguesstimate.viewmodels;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.seroky.weatherguesstimate.providers.GsonRequest;
import com.seroky.weatherguesstimate.models.weather.Results;
import com.seroky.weatherguesstimate.providers.WeatherProvider;

/**
 * View Model for MainActivity
 * Created by derek on 12/10/17.
 */

public class MainViewModel implements WeatherProvider.IWeatherReady
{

    private Results mWeather;
    private RequestQueue mRequestQueue;
    private Context mContext;


    public MainViewModel(Context applicationContext)
    {
        mContext = applicationContext;

//        getWeatherForCity();
    }

    /**
     * Get Weather for the city from the file
     * if not in there, default to Just New York
     */
    private void getWeatherForCity()
    {

        mWeather = (Results) WeatherProvider.ReadCurrentCityFromFile(mContext, Results.class);
        //If we didn't have any info saved or the info is outdated,
        // get new info from the server
        if (mWeather == null )
        {
//            WeatherProvider.getInstance().getWeatherForCity(mContext, );
        }
        else
        {
            System.out.println("Found: " + mWeather.toString());
        }
    }

    @Override
    public Results weatherResults(Results results)
    {
        mWeather = results;
        return null;
    }
}
