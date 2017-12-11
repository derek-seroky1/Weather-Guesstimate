package com.seroky.weatherguesstimate.models.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Wrapper class for Snow
 * Created by derek on 12/10/17.
 */

public class Snow implements Parcelable
{
    /**
     * Amount of Snow in 3 hours
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

    public Snow()
    {
    }

    protected Snow(Parcel in)
    {
        this._3h = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Snow> CREATOR = new Parcelable.Creator<Snow>()
    {
        @Override
        public Snow createFromParcel(Parcel source)
        {
            return new Snow(source);
        }

        @Override
        public Snow[] newArray(int size)
        {
            return new Snow[size];
        }
    };
}
