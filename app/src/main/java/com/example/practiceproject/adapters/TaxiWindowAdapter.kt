package com.example.practiceproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceproject.R
import com.example.practiceproject.TaxiInfo

class TaxiWindowAdapter(private val tags: ArrayList<TaxiInfo>?) : RecyclerView.Adapter<TaxiWindowAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val driverInputArray: TextView = view.findViewById<View>(R.id.driverInputArray) as TextView
        val priceInputArray: TextView = view.findViewById<View>(R.id.priceInputArray) as TextView
        val taxiClassInputArray: TextView = view.findViewById<View>(R.id.taxiClassInputArray) as TextView
        val timeInputArray: TextView = view.findViewById<View>(R.id.timeInputArray) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.taxi_info, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tag = tags?.get(position)
        holder.priceInputArray.text = tag?.price.toString()
        holder.driverInputArray.text = tag?.driver
        holder.taxiClassInputArray.text = tag?.taxiClass
        holder.timeInputArray.text = tag?.time
    }

    override fun getItemCount(): Int {
        return tags?.size ?: 0
    }

}