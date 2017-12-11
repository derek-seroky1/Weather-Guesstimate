package com.seroky.weatherguesstimate.models.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by derek on 12/10/17.
 */

public class WeatherMain implements Parcelable
{
    @SerializedName("temp")
    @Expose
    private Double temp;
    @SerializedName("temp_min")
    @Expose
    private Double tempMin;
    @SerializedName("temp_max")
    @Expose
    private Double tempMax;
    @SerializedName("pressure")
    @Expose
    private Double pressure;
    @SerializedName("sea_level")
    @Expose
    private Double seaLevel;
    @SerializedName("grnd_level")
    @Expose
    private Double grndLevel;
    @SerializedName("humidity")
    @Expose
    private Double humidity;
    @SerializedName("temp_kf")
    @Expose
    private Double tempKf;

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(Double seaLevel) {
        this.seaLevel = seaLevel;
    }

    public Double getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(Double grndLevel) {
        this.grndLevel = grndLevel;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getTempKf() {
        return tempKf;
    }

    public void setTempKf(Double tempKf) {
        this.tempKf = tempKf;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(this.temp);
        dest.writeValue(this.tempMin);
        dest.writeValue(this.tempMax);
        dest.writeValue(this.pressure);
        dest.writeValue(this.seaLevel);
        dest.writeValue(this.grndLevel);
        dest.writeValue(this.humidity);
        dest.writeValue(this.tempKf);
    }

    public WeatherMain()
    {
    }

    protected WeatherMain(Parcel in)
    {
        this.temp = (Double) in.readValue(Double.class.getClassLoader());
        this.tempMin = (Double) in.readValue(Double.class.getClassLoader());
        this.tempMax = (Double) in.readValue(Double.class.getClassLoader());
        this.pressure = (Double) in.readValue(Double.class.getClassLoader());
        this.seaLevel = (Double) in.readValue(Double.class.getClassLoader());
        this.grndLevel = (Double) in.readValue(Double.class.getClassLoader());
        this.humidity = (Double) in.readValue(Double.class.getClassLoader());
        this.tempKf = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<WeatherMain> CREATOR =
            new Parcelable.Creator<WeatherMain>()
            {
                @Override
                public WeatherMain createFromParcel(Parcel source)
                {
                    return new WeatherMain(source);
                }

                @Override
                public WeatherMain[] newArray(int size)
                {
                    return new WeatherMain[size];
                }
            };
}
