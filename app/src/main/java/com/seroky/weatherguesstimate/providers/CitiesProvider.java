package com.seroky.weatherguesstimate.providers;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.seroky.weatherguesstimate.models.city.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Singleton provider
 * Created by derek on 12/10/17.
 */

public class CitiesProvider
{
    //Reference for self
    private static CitiesProvider sCitiesProvider;
    final private AtomicReference<City[]> contents =
            new AtomicReference<>();
    ArrayList<ICitiesReady> mListeners = new ArrayList();

    private List<City> mCities;

    private CitiesProvider(Context context)
    {
        if (contents.get()==null)
        {
            new LoadAssetsThread(context.getAssets()).start();
        }
    }

    public void setListeners(ICitiesReady listener)
    {
        mListeners.add(listener);
    }

    public static CitiesProvider getInstance(Context applicationContext)
    {
        if (sCitiesProvider == null)
        {
            sCitiesProvider = new CitiesProvider(applicationContext);
        }
        return sCitiesProvider;
    }

    public ArrayList<City> getCity(String name)
    {
        ArrayList<City> cities = new ArrayList<>();
        String cityToFind = name.toLowerCase();
        if (mCities != null)
        {
            for (City city : mCities)
            {
                if (city.getName().toLowerCase().contains(cityToFind))
                {
                    cities.add(city);
                }
            }
        }
        return cities;
    }

    /**
     * Private class to load the Asset off the gui thread.
     */
    private class LoadAssetsThread extends Thread
    {
        private AssetManager assets = null;

        LoadAssetsThread(AssetManager assets)
        {
            super();
            this.assets = assets;
        }

        /**
         * If this thread was constructed using a separate
         * <code>Runnable</code> run object, then that
         * <code>Runnable</code> object's <code>run</code> method is called;
         * otherwise, this method does nothing and returns.
         * <p>
         * Subclasses of <code>Thread</code> should override this method.
         *
         * @see #start()
         * @see #stop()
        //         * @see #Thread(ThreadGroup, Runnable, String)
         */
        @Override
        public void run()
        {
            Gson gson =new Gson();
            try {
                InputStream is =assets.open("city.list.json");
                BufferedReader reader=
                        new BufferedReader(new InputStreamReader(is));
                contents.set(gson.fromJson(reader, City[].class));
            }
            catch (IOException e) {
                Log.e(getClass().getSimpleName(), "Exception parsing JSON", e);
            }
            mCities = Arrays.asList(contents.get());
            for (ICitiesReady listener : mListeners)
            {
                listener.CitiesLoaded();
            }
        }


    }

    public interface ICitiesReady
    {
        void CitiesLoaded();
    }
}
