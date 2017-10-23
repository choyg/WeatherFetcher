package com.gchoy.weatherfetcher.display

import com.gchoy.weatherfetcher.weather.WeatherApi
import com.gchoy.weatherfetcher.zipcode.Zipcode
import com.gchoy.weatherfetcher.zipcode.ZipcodeActionType
import com.gchoy.weatherfetcher.zipcode.ZipcodeManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException

class DisplayPresenterImpl(val view: DisplayView,
                           val zipcodeManager: ZipcodeManager,
                           val weatherApi: WeatherApi
) : DisplayPresenter {

    private val compositeDispoable = CompositeDisposable()

    override fun attachView() {
        view.setRVAdapter(zipcodeManager.getZipcodes())
        setZipcodeStream()
    }

    override fun detachView() {
        compositeDispoable.clear()
    }

    override fun addZipcode(zipcode: String) {
        if (!zipcode.isValidZipcode()) {
            view.invalidZipcode()
            return
        }
        // Retrieve weather info for a valid zipcode
        // get info
        // Confirm info with add
        compositeDispoable.add(weatherApi.getCurrentWeather(zipcode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ weather ->
                    val zip = Zipcode(weather.name, zipcode.toInt(), weather.coord.lon, weather.coord.lat)
                    view.confirmZipcode(zip, weather)
                }, { throwable ->
                    if (throwable is HttpException) {
                        when (throwable.code()) {
                            404 -> view.invalidZipcode()
                        }
                    } else {
                        println(throwable)
                        view.error()
                    }
                }))
    }

    override fun confirmZipcode(zipcode: String) {
        compositeDispoable.add(weatherApi.getCurrentWeather(zipcode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ weather ->
                    zipcodeManager.addZipcode(Zipcode(weather.name, zipcode.toInt(), weather.coord.lon, weather.coord.lat))
                }, { throwable ->
                    if (throwable is HttpException) {
                        when (throwable.code()) {
                            404 -> view.invalidZipcode()
                        }
                    } else {
                        view.error()
                    }
                }))
    }

    private fun String.isValidZipcode(): Boolean {
        try {
            this.toInt()
        } catch (ex: Exception) {
            return false
        }
        return true
    }

    private fun setZipcodeStream() {
        compositeDispoable.add(zipcodeManager.getZipcodeStream()
                .subscribe({
                    if (it.action == ZipcodeActionType.ADD)
                        view.addZipcode(it.zipcode)
                    // else view.removeZipcode(it.zipcode)
                }, {
                    throw it
                }))
    }

}