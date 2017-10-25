package com.gchoy.weatherfetcher.details

import com.gchoy.weatherfetcher.weather.WeatherApi
import com.gchoy.weatherfetcher.zipcode.Zipcode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class DetailsPresenterImpl(val view: DetailsView,
                           val zipcode: Zipcode,
                           val weatherApi: WeatherApi
) : DetailsPresenter {

    private val compositeDispoable = CompositeDisposable()

    override fun attachView() {
        compositeDispoable.add(weatherApi.getCurrentWeather(zipcode.zipcode.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ weather ->
                    view.displayWeather(weather)
                }, {
                    view.error()
                }))
    }

    override fun detachView() {
        compositeDispoable.clear()
    }
}