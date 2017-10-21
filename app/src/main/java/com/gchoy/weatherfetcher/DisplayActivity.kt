package com.gchoy.weatherfetcher

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class DisplayActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_layout)
    }
}