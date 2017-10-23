package com.gchoy.weatherfetcher.weather.Entities;

import com.squareup.moshi.Json;

public class Main {

    @Json(name = "temp")
    public double temp;
    @Json(name = "pressure")
    public int pressure;
    @Json(name = "humidity")
    public int humidity;
    @Json(name = "temp_min")
    public double tempMin;
    @Json(name = "temp_max")
    public double tempMax;

}