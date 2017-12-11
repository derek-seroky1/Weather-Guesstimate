package com.seroky.weatherguesstimate.viewmodels;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.seroky.weatherguesstimate.GsonRequest;
import com.seroky.weatherguesstimate.models.weather.Results;
import com.seroky.weatherguesstimate.providers.WeatherProvider;

/**
 * View Model for MainActivity
 * Created by derek on 12/10/17.
 */

public class MainViewModel
{
    private static String FILE_NAME        = "current.city.list.json";
    private static String DEFAULT_CITY     = "NEW YORK CITY"; //This is the default to set if no city is currently selected.
    private static String API_KEY          = "&APPID=4bbf6f21923a4e503dd7b558778da1e0"; //not best way to store this...
    private static String WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/forecast?id=";
    private static String SAMPLE_URL = "https://api.openweathermap.org/data/2.5/forecast?id=5128581&appid=4bbf6f21923a4e503dd7b558778da1e0";

    private Results mWeather;
    private RequestQueue mRequestQueue;
    private Context mContext;


    public MainViewModel(Context applicationContext)
    {
        mContext = applicationContext;
        getWeatherForCity();
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
            mRequestQueue = Volley.newRequestQueue(mContext);
            GsonRequest<Results> gsonWeatherRequest = new GsonRequest<>
                    (SAMPLE_URL, Results.class, null, new Response.Listener<Results>()
                    {
                        @Override
                        public void onResponse(Results results)
                        {
                            mWeather = results;
                            WeatherProvider.StoreResultToFile(mContext, results);

                        }
                    }, new Response.ErrorListener()
                    {

                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            int code = error.networkResponse.statusCode;
                            // TODO Auto-generated method stub
                            System.out.println("Oh NOOOO!!!");
                        }
                    });

            mRequestQueue.add(gsonWeatherRequest);
        }
        else
        {
            System.out.println("Found: " + mWeather.toString());
        }
    }
}
