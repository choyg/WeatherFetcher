package com.gchoy.weatherfetcher.display

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gchoy.weatherfetcher.BaseActivity
import com.gchoy.weatherfetcher.R
import com.gchoy.weatherfetcher.weather.Entities.Weather
import com.gchoy.weatherfetcher.zipcode.Zipcode
import com.gchoy.weatherfetcher.zipcode.ZipcodeManager
import kotlinx.android.synthetic.main.confirm_layout.*
import kotlinx.android.synthetic.main.display_layout.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.zipcode_input.*

class DisplayActivity : DisplayView, BaseActivity() {

    private lateinit var presenter: DisplayPresenter
    private lateinit var zipcodeManager: ZipcodeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_layout)
        setDependencies()
        setActionbar()
        setListeners()
        presenter.attachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Must null to trigger onDetachedFromRecyclerView
        display_recycler.adapter = null
        presenter.detachView()
    }

    override fun addZipcode(zipcode: Zipcode) {
        val zipcodeAdapter = display_recycler.adapter as DisplayRVAdapter
        zipcodeAdapter.addZipcode(zipcode)
    }

    override fun removeZipcode(zipcode: Zipcode) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun promptZipcode() {
        AlertDialog.Builder(this)
                .setTitle(R.string.zipcode_title)
                .setView(R.layout.zipcode_input)
                .setPositiveButton(R.string.zipcode_positive, { dialogInterface: DialogInterface, i: Int ->
                    val zipcode = (dialogInterface as AlertDialog).zipcode_input.text.toString()
                    presenter.addZipcode(zipcode)
                })
                .setNegativeButton(R.string.zipcode_negative, null)
                .create()
                .show()
    }

    override fun confirmZipcode(zipcode: Zipcode, weather: Weather) {
        val dialog = AlertDialog.Builder(this)
                .setTitle(zipcode.zipcode.toString())
                .setView(R.layout.confirm_layout)
                .setPositiveButton(R.string.zipcode_add, { dialogInterface: DialogInterface, i: Int ->
                    presenter.confirmZipcode(zipcode.zipcode.toString())
                })
                .setNegativeButton(R.string.zipcode_cancel, null)
                .create()
        dialog.show()
        dialog.confirm_city.text = weather.name
        dialog.confirm_current.text = getString(R.string.temperature_fahrenheit, weather.main.temp.toString())
        Glide.with(dialog.context)
                .load(weather.getIconUrl())
                .centerCrop()
                .into(dialog.confirm_icon)
    }

    override fun invalidZipcode() {
        error(R.string.zipcode_invalid)
    }

    override fun error(messageId: Int) {
        AlertDialog.Builder(this)
                .setTitle(R.string.zipcode_error_title)
                .setMessage(messageId)
                .setPositiveButton(R.string.zipcode_ok, null)
                .create()
                .show()
    }

    override fun setRVAdapter(data: List<Zipcode>) {
        display_recycler.adapter = DisplayRVAdapter(data)
        display_recycler.layoutManager = LinearLayoutManager(this)
    }

    private fun setListeners() {
        display_fab.setOnClickListener { promptZipcode() }
    }

    private fun setDependencies() {
        zipcodeManager = getZipcodeManager(this)
        presenter = DisplayPresenterImpl(this, zipcodeManager, getWeatherApi())
    }

    private fun setActionbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.display_title)
    }

}