package com.seroky.weatherguesstimate.models.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by derek on 12/10/17.
 */

public class Forecast implements Parcelable
{
    @SerializedName("dt")
    @Expose
    private Integer dt;

    @SerializedName("main")
    @Expose
    private WeatherMain main;

    @SerializedName("weather")
    @Expose
    private java.util.List<Weather> weather = null;

    @SerializedName("clouds")
    @Expose
    private Clouds clouds;

    @SerializedName("wind")
    @Expose
    private Wind wind;

    @SerializedName("snow")
    @Expose
    private Snow snow;

    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public WeatherMain getMain() {
        return main;
    }

    public void setMain(WeatherMain main) {
        this.main = main;
    }

    public java.util.List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Snow getSnow() {
        return snow;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(this.dt);
        dest.writeParcelable(this.main, flags);
        dest.writeList(this.weather);
        dest.writeParcelable(this.clouds, flags);
        dest.writeParcelable(this.wind, flags);
        dest.writeParcelable(this.snow, flags);
        dest.writeString(this.dtTxt);
    }

    public Forecast()
    {
    }

    protected Forecast(Parcel in)
    {
        this.dt = (Integer) in.readValue(Integer.class.getClassLoader());
        this.main = in.readParcelable(WeatherMain.class.getClassLoader());
        this.weather = new ArrayList<Weather>();
        in.readList(this.weather, Weather.class.getClassLoader());
        this.clouds = in.readParcelable(Clouds.class.getClassLoader());
        this.wind = in.readParcelable(Wind.class.getClassLoader());
        this.snow = in.readParcelable(Snow.class.getClassLoader());
        this.dtTxt = in.readString();
    }

    public static final Parcelable.Creator<Forecast> CREATOR = new Parcelable.Creator<Forecast>()
    {
        @Override
        public Forecast createFromParcel(Parcel source)
        {
            return new Forecast(source);
        }

        @Override
        public Forecast[] newArray(int size)
        {
            return new Forecast[size];
        }
    };
}
