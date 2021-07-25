package com.example.hachiojicalendar

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class CalendarAdapter(
    private val dayOfMonth: ArrayList<String>,
    private val onItemListener: OnItemListener
) : RecyclerView.Adapter<CalendarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.166666666).toInt()
        return CalendarViewHolder(view, onItemListener)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayOfMonth.text = dayOfMonth[position]
        holder.imageView.setImageResource(R.drawable.bin)
    }

    override fun getItemCount(): Int {
        return dayOfMonth.size
    }

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String?)
    }
}