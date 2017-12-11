package com.seroky.weatherguesstimate.providers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.seroky.weatherguesstimate.models.city.City;
import com.seroky.weatherguesstimate.models.weather.Forecast;
import com.seroky.weatherguesstimate.models.weather.Results;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is a provider of data from JSON files. Since we were already throwing GSON parsers around
 * I decided to continue using GSON since SQLite would have been additional overhead to maintain
 * with little added benefit for this application.
 * Created by derek on 12/10/17.
 */

public class WeatherProvider
{
    private static String CURRENT_CITY_FILE_NAME    = "current.city.list.json";
    private static String SELECTED_CITIES_FILE_NAME = "selected.cities.list.json";
    private static String FILE_NAME                 = "current.city.list.json";
    private static String DEFAULT_CITY              = "NEW YORK CITY";
            //This is the default to set if no city is currently selected.
    private static String API_KEY                   = "&APPID=4bbf6f21923a4e503dd7b558778da1e0";
            //not best way to store this...
    private static String WEATHER_BASE_URL          =
            "https://api.openweathermap.org/data/2.5/forecast?id=";
    //    private static String SAMPLE_URL = "https://api.openweathermap.org/data/2.5/forecast?id=5128581&appid=4bbf6f21923a4e503dd7b558778da1e0";
    private static String ICON_BASE_URL             = "http://openweathermap.org/img/w/";
    private static WeatherProvider sWeatherProvider;
    private        RequestQueue    mRequestQueue;
    private        Results         mCurrentResults;
    private HashMap<String, Bitmap>  mBitmapMap = new HashMap<>();
    private ArrayList<IWeatherReady> listeners  = new ArrayList<>();
    private AsyncTask mDownloadIconsTask;

    //Singleton class
    private WeatherProvider()
    {
    }

    //Stores the result of the current city to file
    public static void StoreResultToFile(Context context, Results results)
    {
        WriteToFile(context, results, CURRENT_CITY_FILE_NAME);
    }

    public static void StoreSelectedCityToFile(Context context, City city)
    {
        WriteToFile(context, city, SELECTED_CITIES_FILE_NAME);
    }

    @Nullable
    public static Object ReadCurrentCityFromFile(Context context, Type typeOfT)
    {
        Gson gson = new Gson();
        try
        {
            FileInputStream is = context.openFileInput(CURRENT_CITY_FILE_NAME);
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(is));
            return gson.fromJson(reader, Results.class);

        } catch (FileNotFoundException e)
        {
            return null;
        }
    }

    @NonNull
    public static WeatherProvider getInstance()
    {
        if (sWeatherProvider == null)
        {
            sWeatherProvider = new WeatherProvider();
        }
        return sWeatherProvider;
    }

    private static void WriteToFile(Context context, Object object, String fileName)
    {
        // try with resources to make sure we don't corrupt our file
        try (FileOutputStream fos = context
                .openFileOutput(fileName, Context.MODE_PRIVATE))
        {

            Gson gson = new Gson();
            BufferedWriter bufferedWriter =
                    new BufferedWriter(new OutputStreamWriter(fos));
            gson.toJson(object, bufferedWriter);
            bufferedWriter.close();
            fos.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Get Weather for the city from the file
     * if not in there, default to Just New York
     */
    public void getWeatherForCity(final Context context, int cityId, final IWeatherReady listener)
    {
        listeners.add(listener);

        if (mRequestQueue == null)
        {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        StringBuilder stringBuilder = new StringBuilder(WEATHER_BASE_URL);
        stringBuilder.append(cityId);
        stringBuilder.append(API_KEY);
        GsonRequest<Results> gsonWeatherRequest = new GsonRequest<>
                (stringBuilder.toString(), Results.class, null, new Response.Listener<Results>()
                {
                    @Override
                    public void onResponse(Results results)
                    {
                        mCurrentResults = results;
                        WeatherProvider.StoreResultToFile(context, results);
                        getIconForWeatherResultsTask(results);
                    }
                }, new Response.ErrorListener()
                {

                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        int code = error.networkResponse.statusCode;
                        System.out.println("Oh NOOOO!!!");
                    }
                });

        mRequestQueue.add(gsonWeatherRequest);
    }

    private void getIconForWeatherResultsTask(Results results)
    {
        mDownloadIconsTask = new DownloadIconsTask().execute(results);
    }

    public void getIconForWeatherResults(Results results)
    {
        getIconsFromServer(results);
    }

    public Bitmap getIconById(String iconId)
    {
        return mBitmapMap.get(iconId);
    }

    private HashMap<String, Bitmap> getIconsFromServer(Results results)
    {
        HttpURLConnection       connection = null;
        HashMap<String, Bitmap> returnMap  = new HashMap<>();

        for (Forecast forecast : results.getforecast())
        {
            String iconId = forecast.getWeather().get(0).getIcon();
            if (!mBitmapMap.containsKey(iconId) && !returnMap.containsKey(iconId))
            {
                //Get icon from server
                try
                {
                    URL currentURL = null;
                    try
                    {
                        currentURL =
                                new URL(ICON_BASE_URL + iconId + ".png");
                    } catch (MalformedURLException e)
                    {
                        e.printStackTrace();
                    }

                    // Initialize a new http url connection
                    connection = (HttpURLConnection) currentURL.openConnection();

                    // Connect the http url connection
                    connection.connect();

                    // Get the input stream from http url connection
                    InputStream inputStream = connection.getInputStream();

                    // Convert BufferedInputStream to Bitmap object
                    Bitmap bmp = BitmapFactory.decodeStream(inputStream);

                    // Add the bitmap to list
                    returnMap.put(iconId, bmp);

                } catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        mBitmapMap.putAll(returnMap);
        return returnMap;
    }

    public interface IWeatherReady
    {
        Results weatherResults(Results results);
    }

    private class DownloadIconsTask extends AsyncTask<Results, Integer, HashMap<String, Bitmap>>
    {

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param results The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected HashMap<String, Bitmap> doInBackground(Results... results)
        {
            return getIconsFromServer(results[0]);
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param stringBitmapHashMap The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(HashMap<String, Bitmap> stringBitmapHashMap)
        {
            for (IWeatherReady listener : listeners)
            {
                listener.weatherResults(mCurrentResults);
            }
        }
    } //end DownloadIconsTask

} //end WeatherProvider

