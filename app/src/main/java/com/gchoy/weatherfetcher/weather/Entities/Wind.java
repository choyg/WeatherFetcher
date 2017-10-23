package com.gchoy.weatherfetcher.weather.Entities;

import com.squareup.moshi.Json;

public class Wind {

    @Json(name = "speed")
    public double speed;
    @Json(name = "deg")
    public double deg;

}