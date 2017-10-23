package com.gchoy.weatherfetcher

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.gchoy.weatherfetcher.zipcode.ZipcodeManager
import io.reactivex.disposables.CompositeDisposable

open class BaseActivity : AppCompatActivity() {
    val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    protected fun getZipcodeManager(context: Context): ZipcodeManager =
            (application as WeatherApplication).getZipcodeManager()

    protected fun getSharedPref(context: Context): RxSharedPreferences =
            WeatherApplication.getSharedPref(context)
}