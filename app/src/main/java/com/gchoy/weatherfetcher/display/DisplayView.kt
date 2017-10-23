package com.gchoy.weatherfetcher.display

import com.gchoy.weatherfetcher.R
import com.gchoy.weatherfetcher.weather.Entities.Weather
import com.gchoy.weatherfetcher.zipcode.Zipcode

interface DisplayView {
    fun addZipcode(zipcode: Zipcode)
    fun confirmZipcode(zipcode: Zipcode, weather: Weather)
    fun promptZipcode()
    fun invalidZipcode()
    fun error(messageId: Int = R.string.zipcode_error)
    fun removeZipcode(zipcode: Zipcode)
    fun setRVAdapter(data: List<Zipcode>)
}