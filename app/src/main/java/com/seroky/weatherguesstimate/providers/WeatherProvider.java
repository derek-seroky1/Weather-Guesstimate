package com.seroky.weatherguesstimate.providers;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.seroky.weatherguesstimate.models.City;
import com.seroky.weatherguesstimate.models.weather.Results;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

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


    public static void StoreResultToFile(Context context, Results results)
    {
        WriteToFile(context, results, CURRENT_CITY_FILE_NAME );
    }

    public static void StoreSelectedCityToFile(Context context, City city)
    {
        WriteToFile(context, city, SELECTED_CITIES_FILE_NAME);
    }

    @Nullable
    public static Object ReadCurrentCityFromFile(Context context, Type typeOfT )
    {
        Gson gson=new Gson();
        try {
            FileInputStream is = context.openFileInput(CURRENT_CITY_FILE_NAME);
            BufferedReader reader=
                    new BufferedReader(new InputStreamReader(is));
            return gson.fromJson(reader, Results.class);

        }
        catch (FileNotFoundException e)
        {
            return null;
        }
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
}
