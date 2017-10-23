package com.gchoy.weatherfetcher.weather.Entities;

import com.squareup.moshi.Json;

public class Sys {

    @Json(name = "type")
    public int type;
    @Json(name = "id")
    public int id;
    @Json(name = "message")
    public double message;
    @Json(name = "country")
    public String country;
    @Json(name = "sunrise")
    public int sunrise;
    @Json(name = "sunset")
    public int sunset;

}