package com.gchoy.weatherfetcher.weather.Entities;

import com.squareup.moshi.Json;

public class Weather_ {

    @Json(name = "id")
    public int id;
    @Json(name = "main")
    public String main;
    @Json(name = "description")
    public String description;
    @Json(name = "icon")
    public String icon;

}