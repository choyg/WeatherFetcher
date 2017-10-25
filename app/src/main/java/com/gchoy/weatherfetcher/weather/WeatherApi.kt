package com.gchoy.weatherfetcher.weather

import com.gchoy.weatherfetcher.weather.Entities.Weather
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object WeatherApi {
    private lateinit var apiKey: String
    private lateinit var retrofit: Retrofit

    fun register(apiKey: String, moshi: Moshi, retrofitClient: OkHttpClient = OkHttpClient()) {
        this.apiKey = apiKey
        buildRetrofit(moshi, retrofitClient)
    }

    fun getCurrentWeather(zipcode: String): Observable<Weather> {
        try {
            val service = retrofit.create(OpenWeatherMapService::class.java)
            return service.getCurrentWeather(zipcode, apiKey)
        } catch (ex: UninitializedPropertyAccessException) {
            throw UninitializedPropertyAccessException("API not registered. Call register()")
        }
    }

    private fun buildRetrofit(moshi: Moshi, retrofitClient: OkHttpClient) {
        retrofit = Retrofit.Builder()
                .client(retrofitClient)
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
    }
}