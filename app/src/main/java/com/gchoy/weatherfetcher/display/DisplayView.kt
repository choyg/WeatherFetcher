package com.gchoy.weatherfetcher.display

import com.gchoy.weatherfetcher.zipcode.Zipcode

interface DisplayView {
    fun addZipcode(zipcode: Zipcode)
    fun confirmZipcode(zipcode: String)
    fun promptZipcode()
    fun invalidZipcode()
    fun removeZipcode(zipcode: Zipcode)
    fun setRVAdapter(data: List<Zipcode>)
}