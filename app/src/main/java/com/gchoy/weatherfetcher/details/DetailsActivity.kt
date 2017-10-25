package com.gchoy.weatherfetcher.details

import android.os.Bundle
import com.gchoy.weatherfetcher.BaseActivity
import com.gchoy.weatherfetcher.Keys
import com.gchoy.weatherfetcher.R
import com.gchoy.weatherfetcher.zipcode.Zipcode

class DetailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_layout)
        val zipcode = intent.extras.getParcelable<Zipcode>(Keys.zipcode)
        println(zipcode)
    }
}