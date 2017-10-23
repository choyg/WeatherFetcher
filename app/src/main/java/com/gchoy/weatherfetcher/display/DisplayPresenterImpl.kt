package com.gchoy.weatherfetcher.display

import com.gchoy.weatherfetcher.zipcode.Zipcode
import com.gchoy.weatherfetcher.zipcode.ZipcodeActionType
import com.gchoy.weatherfetcher.zipcode.ZipcodeManager
import io.reactivex.disposables.CompositeDisposable

class DisplayPresenterImpl(val view: DisplayView, val zipcodeManager: ZipcodeManager) : DisplayPresenter {

    private val compositeDispoable = CompositeDisposable()

    override fun attachView() {
        view.setRVAdapter(zipcodeManager.getZipcodes())
        setZipcodeStream()
    }

    override fun detachView() {
        compositeDispoable.clear()
    }

    override fun addZipcode(zipcode: String) {
        if (!validateZipcode(zipcode)) {
            view.invalidZipcode()
            println("Invalid zipcode")
            return
        }
        // Retrieve weather info for a valid zipcode
        // get info
        // Confirm info with add
        view.confirmZipcode(zipcode)
    }

    override fun confirmZipcode(zipcode: String) {
        // zipcodeObj = backend retrieve zipcode
        val zipcodeObj = Zipcode("City name", zipcode.toInt(), 0.0, 0.0)
        zipcodeManager.addZipcode(zipcodeObj)
    }

    private fun validateZipcode(zipcode: String): Boolean {
        return true // TODO
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