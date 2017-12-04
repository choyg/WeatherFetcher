package com.gchoy.weatherfetcher

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.gchoy.weatherfetcher.weather.WeatherApi
import com.gchoy.weatherfetcher.zipcode.Zipcode
import com.gchoy.weatherfetcher.zipcode.ZipcodeManager
import com.gchoy.weatherfetcher.zipcode.ZipcodeManagerSharedPref
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.OkHttpClient

class WeatherApplication : Application() {
    companion object {

        private val moshiStatic = Moshi.Builder().build()

        private fun zipcodeManager(context: Context): ZipcodeManager =
                ZipcodeManagerSharedPref(getSharedPref(context), moshiStatic)

        private fun okHttp(context: Context): OkHttpClient {
            val cacheSize = 10 * 1024 * 1024L // 10MB
            val cacheTime = 60 * 60 // 1 hour
            return OkHttpClient.Builder()
                    .cache(Cache(context.cacheDir, cacheSize))
                    .addNetworkInterceptor { chain ->
                        val request = chain.request()
                        val response = chain.proceed(request)
                                .newBuilder()
                                .addHeader("Cache-Control", "max-age=" + cacheTime)
                                .build()
                        response
                    }
                    .build()
        }

        private fun weatherApi(apiKey: String, context: Context): WeatherApi {
            val api = WeatherApi
            api.register(apiKey, moshiStatic, okHttp(context))
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
        weatherAPI = Companion.weatherApi(getString(R.string.openweathermap_key), this)
        prepopulate()
    }

    /**
     *  If this is the user's first time running WeatherFetcher
     *  prepopulate with 3 unique entries
     */
    private fun prepopulate() {
        val sharedPref = getSharedPref(this)
        val firstTime = sharedPref.getBoolean(Keys.firstInstall, true)
        if (!firstTime.get()) return

        val zipcode1 = Zipcode("San Diego", "92130", -117.17, 32.8)
        val zipcode2 = Zipcode("Austin", "73301", -97.77, 30.33)
        val zipcode3 = Zipcode("Washington", "20001", -77.02, 38.91)

        val zipcodeManager = getZipcodeManager()
        zipcodeManager.addZipcode(zipcode1)
        zipcodeManager.addZipcode(zipcode2)
        zipcodeManager.addZipcode(zipcode3)

        firstTime.set(false)
    }
}