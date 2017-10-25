package com.gchoy.weatherfetcher.display

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gchoy.weatherfetcher.R
import com.gchoy.weatherfetcher.zipcode.Zipcode
import kotlinx.android.synthetic.main.zipcode_tile.view.*

class DisplayRVAdapter(initialData: List<Zipcode>, val listener: ZipcodeClickListener
) : RecyclerView.Adapter<DisplayRVAdapter.ZipcodeViewHolder>() {

    private val data = initialData.toMutableList()

    override fun onBindViewHolder(holder: ZipcodeViewHolder, position: Int) {
        val zipcodeInfo = data[position]
        holder.zipcodeText.text = zipcodeInfo.zipcode.toString()
        holder.zipcode = zipcodeInfo
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ZipcodeViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        return ZipcodeViewHolder(layoutInflater.inflate(R.layout.zipcode_tile, parent, false), listener)
    }

    fun addZipcode(zipcode: Zipcode) {
        data.add(zipcode)
        notifyItemInserted(data.lastIndex)
    }

    class ZipcodeViewHolder(itemView: View, val listener: ZipcodeClickListener) : RecyclerView.ViewHolder(itemView) {
        val zipcodeText = itemView.zipcode_text
        lateinit var zipcode: Zipcode

        init {
            itemView.setOnClickListener { listener.onClick(zipcode) }
        }
    }

    interface ZipcodeClickListener {
        fun onClick(zipcode: Zipcode)
    }
}