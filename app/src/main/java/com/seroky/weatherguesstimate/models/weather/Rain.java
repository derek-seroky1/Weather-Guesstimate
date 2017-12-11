package com.seroky.weatherguesstimate.models.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by derek on 12/10/17.
 */

public class Rain implements Parcelable
{
    /**
     * Amount of Rain in 3 hours
     */
    @SerializedName("3h")
    @Expose
    private Double _3h;

    public Double get3h() {
        return _3h;
    }

    public void set3h(Double _3h) {
        this._3h = _3h;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(this._3h);
    }

    public Rain()
    {
    }

    protected Rain(Parcel in)
    {
        this._3h = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Rain> CREATOR = new Parcelable.Creator<Rain>()
    {
        @Override
        public Rain createFromParcel(Parcel source)
        {
            return new Rain(source);
        }

        @Override
        public Rain[] newArray(int size)
        {
            return new Rain[size];
        }
    };
}
