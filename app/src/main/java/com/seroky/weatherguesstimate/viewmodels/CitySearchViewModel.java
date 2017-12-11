package com.seroky.weatherguesstimate.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.seroky.weatherguesstimate.R;
import com.seroky.weatherguesstimate.models.city.City;
import com.seroky.weatherguesstimate.models.weather.Forecast;
import com.seroky.weatherguesstimate.views.CityDetailActivity;
import com.seroky.weatherguesstimate.views.CityListViewActivity;

/**
 * The view model for the CitySearchActivity which contains the adapter for the listview
 *  to display results.
 *
 * Created by derek on 12/10/17.
 */

public class CitySearchViewModel extends ArrayAdapter<City>
{

    private Context mContext;
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
        mContext = context;
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

        convertView.setTag(city);
        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, CityListViewActivity.class);
                intent.putExtra("city",(City) v.getTag());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }
}
