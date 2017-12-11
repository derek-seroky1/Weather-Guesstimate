package com.seroky.weatherguesstimate.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.seroky.weatherguesstimate.R;
import com.seroky.weatherguesstimate.models.weather.Forecast;
import com.seroky.weatherguesstimate.models.weather.Weather;
import com.seroky.weatherguesstimate.models.weather.WeatherMain;
import com.seroky.weatherguesstimate.providers.WeatherProvider;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CityDetailActivity extends AppCompatActivity
{
    private static double FAHRENHEIGHT_CONSTANT = 9.0 / 5.0;
    private static char DEGREE_SYMBOL = '\u00B0';

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);

        Intent intent = getIntent();

        Forecast forecast = intent.getParcelableExtra("forecast");
        Log.d(getClass().getSimpleName(), forecast.getWeather().get(0).getDescription());

        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        String           dateString = dateFormat.format( new Date((long) forecast.getDt() * 1000));
        myToolbar.setTitle(dateString);
        TextView dtTextView = findViewById(R.id.date_time_textView);
        TextView currentTempTextView = findViewById(R.id.forecast_detail_current_temp_textView);
        TextView tempHighTextView = findViewById(R.id.temp_high_textView);
        TextView tempLowTextView = findViewById(R.id.temp_low_textView);
        TextView humidityTextView = findViewById(R.id.humidity_textView);
        ImageView weatherIconImageView = findViewById(R.id.forecast_detail_weather_icon);

        //-- Date --//
        dateFormat = new SimpleDateFormat("EEE, MMM dd HH:MM");
        dateString = dateFormat.format( new Date((long) forecast.getDt() * 1000));
        dtTextView.setText(dateString);
        WeatherMain weatherMain = forecast.getMain();

        StringBuilder sb = new StringBuilder(String.valueOf ((int) (FAHRENHEIGHT_CONSTANT *
                                                                    (weatherMain.getTemp() - 273) + 32) ));
        sb.append(DEGREE_SYMBOL);
        sb.append("F");
        currentTempTextView.setText(sb.toString());
        sb = new StringBuilder("High: ");
        sb.append(String.valueOf ((int) (FAHRENHEIGHT_CONSTANT *
                                         (weatherMain.getTempMax() - 273) + 32) ));
        sb.append(DEGREE_SYMBOL);
        sb.append("F");

        tempHighTextView.setText(sb.toString());
        sb = new StringBuilder("Low: ");
        sb.append(String.valueOf ((int) (FAHRENHEIGHT_CONSTANT * (weatherMain.getTempMin() - 273) + 32)));
        sb.append(DEGREE_SYMBOL);
        sb.append("F");

        tempLowTextView.setText(sb.toString());

        sb = new StringBuilder("Humidity");
        sb.append(String.valueOf(forecast.getMain().getHumidity()));
        sb.append("%");
        humidityTextView.setText(sb.toString());
        weatherIconImageView.setImageBitmap(WeatherProvider.getInstance().getIconById(forecast.getWeather().get(0).getIcon()));

    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs)
    {


        return super.onCreateView(name, context, attrs);
    }
}
