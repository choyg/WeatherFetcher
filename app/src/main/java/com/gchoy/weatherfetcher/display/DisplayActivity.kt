package com.gchoy.weatherfetcher.display

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import com.gchoy.weatherfetcher.BaseActivity
import com.gchoy.weatherfetcher.R
import com.gchoy.weatherfetcher.zipcode.Zipcode
import com.gchoy.weatherfetcher.zipcode.ZipcodeManager
import kotlinx.android.synthetic.main.display_layout.*
import kotlinx.android.synthetic.main.zipcode_input.*

class DisplayActivity : DisplayView, BaseActivity() {

    private lateinit var presenter: DisplayPresenter
    private lateinit var zipcodeManager: ZipcodeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_layout)
        setDependencies()
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
        val dialog = AlertDialog.Builder(this)
                .setTitle(R.string.zipcode_title)
                .setView(R.layout.zipcode_input)
                .setPositiveButton(R.string.zipcode_positive, { dialogInterface: DialogInterface, i: Int ->
                    val zipcode = (dialogInterface as AlertDialog).zipcode_input.text.toString()
                    presenter.addZipcode(zipcode)
                })
                .setNegativeButton(R.string.zipcode_negative, null)
                .create()
        dialog.show()
    }

    override fun confirmZipcode(zipcode: String) {
        val dialog = AlertDialog.Builder(this)
                .setTitle(R.string.zipcode_confirm)
                .setView(R.layout.zipcode_input)
                .setPositiveButton(R.string.zipcode_add, { dialogInterface: DialogInterface, i: Int ->
                    presenter.confirmZipcode(zipcode)
                })
                .setNegativeButton(R.string.zipcode_cancel, null)
                .create()
        dialog.show()
    }

    override fun invalidZipcode() {

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

}