package com.gchoy.weatherfetcher.details

import com.gchoy.weatherfetcher.weather.Entities.Weather

interface DetailsView {
    fun displayWeather(weather: Weather)
    fun error()
}