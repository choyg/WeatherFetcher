package com.gchoy.weatherfetcher

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.gchoy.weatherfetcher.weather.WeatherApi
import com.gchoy.weatherfetcher.zipcode.ZipcodeManager
import com.gchoy.weatherfetcher.zipcode.ZipcodeManagerSharedPref
import com.squareup.moshi.Moshi

class WeatherApplication : Application() {
    companion object {

        private val moshiStatic = Moshi.Builder().build()

        private fun zipcodeManager(context: Context): ZipcodeManager =
                ZipcodeManagerSharedPref(getSharedPref(context), moshiStatic)

        private fun weatherApi(apiKey: String): WeatherApi {
            val api = WeatherApi
            api.register(apiKey, moshiStatic)
            return api
        }

        fun getSharedPref(context: Context): RxSharedPreferences {
            val preferences = RxSharedPreferences.create(PreferenceManager.getDefaultSharedPreferences(context))
            val rxPreferences = (preferences)
            return rxPreferences
        }
    }

    private lateinit var zipcodeManager: ZipcodeManager
    private lateinit var weatherAPI: WeatherApi
    fun getZipcodeManager(): ZipcodeManager = zipcodeManager
    fun getWeatherApi(): WeatherApi = weatherAPI

    override fun onCreate() {
        super.onCreate()
        zipcodeManager = Companion.zipcodeManager(this)
        weatherAPI = Companion.weatherApi(getString(R.string.openweathermap_key))
    }

}