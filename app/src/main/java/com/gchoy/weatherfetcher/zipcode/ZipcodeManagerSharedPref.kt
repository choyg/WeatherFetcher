package com.gchoy.weatherfetcher.zipcode

import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.gchoy.weatherfetcher.Keys
import com.jakewharton.rxrelay2.PublishRelay
import com.squareup.moshi.Moshi
import io.reactivex.Observable

class ZipcodeManagerSharedPref(sharedPref: RxSharedPreferences, moshi: Moshi) : ZipcodeManager {
    private val zipcodeRelay = PublishRelay.create<ZipcodeAction>()
    private val zipcodePref = sharedPref.getStringSet(Keys.zipcodes)
    private val adapter = moshi.adapter(Zipcode::class.java)
    private var zipcodes: MutableSet<Zipcode>

    init {
        // Initialize zipcodes
        val jsonSet = zipcodePref.get()
        zipcodes = jsonSet
                .mapNotNull { adapter.fromJson(it) }
                .toHashSet()
    }

    override fun addZipcode(zipcode: Zipcode): Boolean {
        // Try to add zipcode to set. End if fails.
        if (!zipcodes.add(zipcode)) return false

        zipcodeRelay.accept(ZipcodeAction(zipcode, ZipcodeActionType.ADD))
        val zipcodeJson = adapter.toJson(zipcode)
        val jsonSet = HashSet(zipcodePref.get())
        jsonSet.add(zipcodeJson)
        zipcodePref.set(jsonSet)
        return true
    }

    override fun containsZipcode(zipcode: Zipcode): Boolean = zipcodes.contains(zipcode)

    override fun getZipcodes(): List<Zipcode> = zipcodePref.get().mapNotNull { adapter.fromJson(it) }

    override fun getZipcodeStream(): Observable<ZipcodeAction> = zipcodeRelay

    override fun removeZipcode(zipcode: Zipcode) {
        if (!zipcodes.remove(zipcode)) return
        zipcodeRelay.accept(ZipcodeAction(zipcode, ZipcodeActionType.REMOVE))
        val zipcodeJson = adapter.toJson(zipcode)
        val jsonSet = HashSet(zipcodePref.get())
        jsonSet.remove(zipcodeJson)
        zipcodePref.set(jsonSet)
    }

}