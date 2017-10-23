package com.gchoy.weatherfetcher.weather

import com.gchoy.weatherfetcher.weather.Entities.Weather
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapService {
    @GET("weather")
    fun getCurrentWeather(@Query("zip") zip: String, @Query("appid") key: String): Observable<Weather>
}