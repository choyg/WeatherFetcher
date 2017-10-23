package com.gchoy.weatherfetcher.zipcode

import io.reactivex.Observable

interface ZipcodeManager {
    fun getZipcodeStream(): Observable<ZipcodeAction>
    fun addZipcode(zipcode: Zipcode): Boolean
    fun removeZipcode(zipcode: Zipcode)
    fun containsZipcode(zipcode: Zipcode): Boolean
    fun getZipcodes(): List<Zipcode>
}