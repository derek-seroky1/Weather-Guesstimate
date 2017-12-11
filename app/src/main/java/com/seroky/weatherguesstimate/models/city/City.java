package com.seroky.weatherguesstimate.models.city;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Wrapper class for data about cities, used for finding the city Id which is used to query the server
 * Created by derek on 12/9/17.
 */

public class City implements Parcelable
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String  name;
    @SerializedName("country")
    @Expose
    private String  country;
    @SerializedName("coord")
    @Expose
    private Coord   coord;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.country);
        dest.writeParcelable(this.coord, flags);
    }

    public City()
    {
    }

    protected City(Parcel in)
    {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.country = in.readString();
        this.coord = in.readParcelable(Coord.class.getClassLoader());
    }

    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>()
    {
        @Override
        public City createFromParcel(Parcel source)
        {
            return new City(source);
        }

        @Override
        public City[] newArray(int size)
        {
            return new City[size];
        }
    };
}
