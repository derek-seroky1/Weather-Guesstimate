package com.seroky.weatherguesstimate.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seroky.weatherguesstimate.R;
import com.seroky.weatherguesstimate.models.city.City;
import com.seroky.weatherguesstimate.models.weather.Forecast;
import com.seroky.weatherguesstimate.models.weather.Results;
import com.seroky.weatherguesstimate.models.weather.WeatherMain;
import com.seroky.weatherguesstimate.providers.CitiesProvider;
import com.seroky.weatherguesstimate.providers.WeatherProvider;
import com.seroky.weatherguesstimate.views.CityDetailActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by derek on 12/9/17.
 */

public class CitiesViewModel extends RecyclerView.Adapter<CitiesViewModel.ViewHolder> implements WeatherProvider.IWeatherReady
{
    private static String DEFAULT_CITY = "New York";
    private static int    TIME_BLOCKS           = 8;
    private static double FAHRENHEIGHT_CONSTANT = 9.0 / 5.0;
    private static char DEGREE_SYMBOL = '\u00B0';
    private List<Forecast> mForecast;
    private int mCurrentTimeBlock;
    private CitiesViewModel mInstance;
    private AsyncTask mInitDataTask;

    // Store the context for easy access
    private Context mContext;
//    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;

    public City mCurrentCity;

    public CitiesViewModel(Context context, RecyclerView recyclerView)
    {
        init(context, recyclerView);
    }

    public CitiesViewModel(Context context, RecyclerView recyclerView, City city)
    {
        mCurrentCity = city;
        init(context, recyclerView);
    }

    private void init(Context context, RecyclerView recyclerView)
    {
        mContext = context;
        mRecyclerView = recyclerView;
        mInstance = this;
        mInitDataTask = new InitDataTask().execute(context);
    }

    // Store a member variable for the contacts
    private List<City> mCities;



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
        int index = position * TIME_BLOCKS;
        holder.bind(mForecast.get(index));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount()
    {
        return mForecast.size() / TIME_BLOCKS;
    }

    @Override
    public Results weatherResults(Results results)
    {
        if (results != null)
        {
            mForecast = results.getforecast();
            mCurrentCity = results.getCity();
        }
        enableRecyclerView();
        return null;
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder
    {

        // The member variable for every view that will be set as we render the row
        public final TextView forecastDescriptionTextView;
        public final TextView dateTimeTextView;
        public final ImageView weatherIconImageView;
        public final TextView currentTempTextView;
        public final TextView highLowTempTextView;

        private Forecast mForecast;

        public ViewHolder(View itemView)
        {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(mContext, CityDetailActivity.class);
                    intent.putExtra("forecast",(Forecast) v.getTag());
                    mContext.startActivity(intent);
                }
            });
            forecastDescriptionTextView = itemView.findViewById(R.id.forecast_description);
            dateTimeTextView = itemView.findViewById(R.id.date_time);
            weatherIconImageView = itemView.findViewById(R.id.current_weather_icon);
            currentTempTextView = itemView.findViewById(R.id.current_temperature);
            highLowTempTextView = itemView.findViewById(R.id.high_low_temp);
        }

        public void bind(final Forecast forecastItem )
        {
            itemView.setTag(forecastItem);
            // Set item views based on views and data model
            mForecast = forecastItem;
            //-- Weather Description --//
            forecastDescriptionTextView.setText(forecastItem.getWeather().get(0).getDescription());
            //-- Weather Icon --//
            weatherIconImageView.setImageBitmap(WeatherProvider.getInstance().getIconById(forecastItem.getWeather().get(0).getIcon()));
            //-- Date --//
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd");
            String dateString = dateFormat.format( new Date((long) forecastItem.getDt() * 1000));
            dateTimeTextView.setText(dateString);

            //-- Current Temperature --//
            WeatherMain weatherMain = forecastItem.getMain();

            StringBuilder sb = new StringBuilder(String.valueOf ((int) (FAHRENHEIGHT_CONSTANT *
                                                                        (weatherMain.getTemp() - 273) + 32) ));
            sb.append(DEGREE_SYMBOL);
            sb.append("F");
            currentTempTextView.setText(sb.toString());

            //-- Min/Max Temperature --//
            sb = new StringBuilder(String.valueOf ((int) (FAHRENHEIGHT_CONSTANT *
                                                                                   (forecastItem.getMain().getTempMin() - 273) + 32)));
            sb.append(DEGREE_SYMBOL);
            sb.append("F");
            sb.append(" / ");
            sb.append(String.valueOf ((int) (FAHRENHEIGHT_CONSTANT *
                                                        (forecastItem.getMain().getTempMax() - 273) + 32)));
            sb.append(DEGREE_SYMBOL);
            sb.append("F");
            highLowTempTextView.setText(sb);
        }
    }

    private void enableRecyclerView()
    {
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        this.notifyDataSetChanged();
    }

    private class InitDataTask extends AsyncTask<Context, Integer, Void>
    {

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param contexts The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Context... contexts)
        {
            Results results = null;
            if (mCurrentCity != null)
            {
                results = (Results) WeatherProvider.ReadCurrentCityFromFile(contexts[0], Results.class);
                if (results.getCity().getId() != mCurrentCity.getId())
                {
                    results = null;
                }
            }
            if (results == null)
            {
                // Initialize the progress dialog
//            mProgressDialog = new ProgressDialog(mContext);
//            mProgressDialog.setIndeterminate(true);
//            // Progress dialog horizontal style
//            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            mProgressDialog.show();

                //Search for the default city
                if (mCurrentCity == null)
                {
                    ArrayList<City> cities = CitiesProvider.getInstance(mContext).getCity(DEFAULT_CITY);
                    //Use first match as the city to fetch
                    WeatherProvider.getInstance().getWeatherForCity(mContext, cities.get(0).getId(), mInstance );
                }
                else
                {
                    WeatherProvider.getInstance().getWeatherForCity(mContext, mCurrentCity.getId(), mInstance );
                }
            }
            else
            {
                mCurrentCity = results.getCity();
                mForecast = results.getforecast();
                WeatherProvider.getInstance().getIconForWeatherResults(results);

                //this has the results stored every 3 hours therefore 8 indexes is one day
                //Therefore, we need the current time to figure out which "block of time" we are in to
                //figure out what to display
                Calendar calendar    = Calendar.getInstance();
                int      currentHour = calendar.get(Calendar.HOUR_OF_DAY);

                if (currentHour != 0) //midnight
                {
                    //this will give the current time block and truncate the remainder
                    mCurrentTimeBlock = currentHour / TIME_BLOCKS;
                } else
                {
                    mCurrentTimeBlock = 0;
                }
            }
            return null;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param aVoid The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(Void aVoid)
        {
            if (mForecast != null)
            {
                enableRecyclerView();
            }
        }
    }
}
