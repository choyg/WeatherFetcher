package com.gchoy.weatherfetcher.details

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.gchoy.weatherfetcher.BaseActivity
import com.gchoy.weatherfetcher.Keys
import com.gchoy.weatherfetcher.R
import com.gchoy.weatherfetcher.weather.Entities.Weather
import com.gchoy.weatherfetcher.zipcode.Zipcode
import kotlinx.android.synthetic.main.details_layout.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailsActivity : BaseActivity(), DetailsView {

    private lateinit var presenter: DetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_layout)
        val zipcode = intent.extras.getParcelable<Zipcode>(Keys.zipcode)
        setInitial(zipcode)
        setActionBar(zipcode)
        presenter = DetailsPresenterImpl(this, zipcode, getWeatherApi())
        presenter.attachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return false
    }

    override fun displayWeather(weather: Weather) {
        details_temperature.text = getString(R.string.temperature_fahrenheit, weather.main.temp.toString())
        details_minmax.text = getString(
                R.string.temperature_range,
                weather.main.tempMin.toString(),
                weather.main.tempMax.toString())
        details_weather_desc.text = weather.weather[0].description
        Glide.with(this)
                .load(weather.getIconUrl())
                .centerCrop()
                .into(details_icon)
    }

    override fun error() {
        details_error.text = getString(R.string.zipcode_error)
        details_error.visibility = View.VISIBLE
    }

    private fun setInitial(zipcode: Zipcode) {
        details_city.text = zipcode.name
    }

    private fun setActionBar(zipcode: Zipcode) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = zipcode.zipcode.toString()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}