package com.seroky.weatherguesstimate.models.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.seroky.weatherguesstimate.models.City;

/**
 * Created by derek on 12/10/17.
 */

public class Results
{
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<Forecast> forecast = null;
    @SerializedName("city")
    @Expose
    private City city;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public java.util.List<Forecast> getforecast() {
        return forecast;
    }

    public void setforecast(java.util.List<Forecast> forecast) {
        this.forecast = forecast;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
