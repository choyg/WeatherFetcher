package com.gchoy.weatherfetcher.display

interface DisplayPresenter {
    fun attachView()
    fun detachView()
    fun addZipcode(zipcode: String)
    fun confirmZipcode(zipcode: String)
}