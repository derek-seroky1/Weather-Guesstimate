package com.seroky.weatherguesstimate.viewmodels;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.seroky.weatherguesstimate.R;
import com.seroky.weatherguesstimate.models.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by derek on 12/9/17.
 */

public class CitiesViewModel extends RecyclerView.Adapter<CitiesViewModel.ViewHolder>
{
    private AssetManager assets = null;

    public CitiesViewModel(Context context)
    {
        mContext = context;
    }

    // Store a member variable for the contacts
    private List<City> mCities;
    // Store the context for easy access
    private Context mContext;


    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View cityView = inflater.inflate(R.layout.city_cell, parent, false);

        // Return a new holder instance
        return new ViewHolder(cityView);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        // Get the data model based on position
        City city = mCities.get(position);

        // Set item views based on your views and data model
        //TODO finish filling this out with real data
        holder.cityNameTextView.setText(city.getName());
        holder.dateTimeTextView.setText("Saturday") ;
        holder.currentTempTextView.setText("32");
        holder.highLowTempTextView.setText("21/32");
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount()
    {
        return mCities.size();
    }



    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder
    {

        // The member variable for every view that will be set as we render the row
        public final TextView cityNameTextView;
        public final TextView dateTimeTextView;
        public final TextView currentTempTextView;
        public final TextView highLowTempTextView;

        public ViewHolder(View itemView)
        {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            cityNameTextView = itemView.findViewById(R.id.city_name);
            dateTimeTextView = itemView.findViewById(R.id.date_time);
            currentTempTextView = itemView.findViewById(R.id.current_temperature);
            highLowTempTextView = itemView.findViewById(R.id.high_low_temp);
        }
    }
}
