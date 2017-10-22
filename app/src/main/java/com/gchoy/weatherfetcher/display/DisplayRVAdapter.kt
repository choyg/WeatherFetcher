package com.gchoy.weatherfetcher.display

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gchoy.weatherfetcher.R
import com.gchoy.weatherfetcher.zipcode.Zipcode
import kotlinx.android.synthetic.main.zipcode_tile.view.*

class DisplayRVAdapter(initialData: List<Zipcode>) : RecyclerView.Adapter<DisplayRVAdapter.ZipcodeViewHolder>() {

    private val data = initialData.toMutableList()

    override fun onBindViewHolder(holder: ZipcodeViewHolder, position: Int) {
        val zipcodeInfo = data[position]
        holder.zipcode.text = zipcodeInfo.zipcode.toString()
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ZipcodeViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        return ZipcodeViewHolder(layoutInflater.inflate(R.layout.zipcode_tile, parent, false))
    }

    fun addZipcode(zipcode: Zipcode) {
        data.add(zipcode)
        notifyItemInserted(data.lastIndex)
    }

    class ZipcodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val zipcode = itemView.zipcode_text
    }
}