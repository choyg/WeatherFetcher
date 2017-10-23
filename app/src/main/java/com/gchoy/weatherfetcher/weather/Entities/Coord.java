package com.gchoy.weatherfetcher.weather.Entities;

import com.squareup.moshi.Json;

public class Coord {

    @Json(name = "lon")
    public double lon;
    @Json(name = "lat")
    public double lat;

}