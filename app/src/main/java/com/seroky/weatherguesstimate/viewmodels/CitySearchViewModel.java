package com.seroky.weatherguesstimate.viewmodels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.seroky.weatherguesstimate.R;
import com.seroky.weatherguesstimate.models.City;

/**
 * The view model for the CitySearchActivity which contains the adapter for the listview
 *  to display results.
 *
 * Created by derek on 12/10/17.
 */

public class CitySearchViewModel extends ArrayAdapter<City>
{
    /**
     * Constructor
     *
     * @param context  The current context.
     * @param objects  The objects to represent in the ListView.
     */
    public CitySearchViewModel(@NonNull Context context,
                               @NonNull City[] objects)
    {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        //Get the item at the position
        City city = getItem(position);
        if (convertView == null) {
            //inflate the view into the cell
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.city_search_cell, parent, false);
        }

        //populate the views with the data from the city object
        if (city != null)
        {
            TextView cityNameTextView = convertView.findViewById(R.id.city_search_name);
            cityNameTextView.setText(city.getName());

            TextView cityCountryTextView = convertView.findViewById(R.id.city_search_country);
            cityCountryTextView.setText(city.getCountry());
        }
        return convertView;
    }
}
