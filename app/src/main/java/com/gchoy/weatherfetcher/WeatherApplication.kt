package com.gchoy.weatherfetcher

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.gchoy.weatherfetcher.zipcode.ZipcodeManager
import com.gchoy.weatherfetcher.zipcode.ZipcodeManagerSharedPref
import com.squareup.moshi.Moshi

class WeatherApplication : Application() {
    companion object {

        private val moshiStatic = Moshi.Builder()
                .build()

        fun getBookmarkManager(context: Context): ZipcodeManager =
                ZipcodeManagerSharedPref(getSharedPref(context))

        fun getSharedPref(context: Context): RxSharedPreferences {
            val preferences = RxSharedPreferences.create(PreferenceManager.getDefaultSharedPreferences(context))
            val rxPreferences = (preferences)
            return rxPreferences
        }
    }
}