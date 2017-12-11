package com.seroky.weatherguesstimate.models.city;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Wrapper class for location of the city. Provided for future enhancements to use current location
 * Created by derek on 12/9/17.
 */

public class Coord implements Parcelable
{
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("lat")
    @Expose
    private Double lat;

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(this.lon);
        dest.writeValue(this.lat);
    }

    public Coord()
    {
    }

    protected Coord(Parcel in)
    {
        this.lon = (Double) in.readValue(Double.class.getClassLoader());
        this.lat = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Coord> CREATOR = new Parcelable.Creator<Coord>()
    {
        @Override
        public Coord createFromParcel(Parcel source)
        {
            return new Coord(source);
        }

        @Override
        public Coord[] newArray(int size)
        {
            return new Coord[size];
        }
    };
}
