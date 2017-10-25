package com.gchoy.weatherfetcher.weather.Entities

import com.squareup.moshi.Json

data class Weather(
        @Json(name = "coord") val coord: Coord,
        @Json(name = "weather") val weather: List<Weather_>,
        @Json(name = "base") val base: String,
        @Json(name = "main") val main: Main,
        @Json(name = "visibility") val visibility: Int,
        @Json(name = "wind") val wind: Wind,
        @Json(name = "clouds") val clouds: Clouds,
        @Json(name = "dt") val dt: Int,
        @Json(name = "sys") val sys: Sys,
        @Json(name = "id") val id: Int,
        @Json(name = "name") val name: String,
        @Json(name = "cod") val cod: Int = 0
) {
    fun getIconUrl(): String = "http://openweathermap.org/img/w/${weather[0].icon}.png"
}