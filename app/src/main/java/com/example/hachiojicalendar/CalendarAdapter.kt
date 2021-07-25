package com.example.hachiojicalendar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class CalendarAdapter(
    private val dayOfMonth: ArrayList<String>,
    private val onItemListener: OnItemListener
) : RecyclerView.Adapter<CalendarViewHolder>() {
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.166666666).toInt()
        context = parent.context
        return CalendarViewHolder(view, onItemListener)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayOfMonth.text = dayOfMonth[position]
        if (position%2 == 0) {
            var secondImage:ImageView = ImageView(context)
            var thirdImage:ImageView = ImageView(context)

            secondImage.setImageResource(R.drawable.gomibukuro_yellow)
            thirdImage.setImageResource(R.drawable.gomibukuro_blue)
            val viewParamsCenter = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            secondImage.layoutParams = viewParamsCenter
            viewParamsCenter.weight++
            thirdImage.layoutParams = viewParamsCenter
            holder.imageLayout.addView(secondImage)
            holder.imageLayout.addView(thirdImage)
        } else {
        }
    }

    override fun getItemCount(): Int {
        return dayOfMonth.size
    }

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String?)
    }
}