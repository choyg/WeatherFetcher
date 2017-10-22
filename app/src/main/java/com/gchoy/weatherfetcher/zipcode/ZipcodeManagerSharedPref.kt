package com.gchoy.weatherfetcher.zipcode

import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

class ZipcodeManagerSharedPref(sharedPref: RxSharedPreferences) : ZipcodeManager {
    private val zipcodeRelay = PublishRelay.create<ZipcodeAction>()

    override fun addZipcode(zipcode: Zipcode) {
        zipcodeRelay.accept(ZipcodeAction(zipcode, ZipcodeActionType.ADD))
        println("manager add zipcode")
    }

    override fun containsZipcode(zipcode: Zipcode): Boolean {
        return false
    }

    override fun getZipcodes(): List<Zipcode> {
        return listOf(Zipcode("City 1", 12345, 0.0, 0.0), Zipcode("City 2", 12345, 0.0, 0.0))
    }

    override fun getZipcodeStream(): Observable<ZipcodeAction> = zipcodeRelay

    override fun removeZipcode(zipcode: Zipcode) {
        zipcodeRelay.accept(ZipcodeAction(zipcode, ZipcodeActionType.REMOVE))
    }

}