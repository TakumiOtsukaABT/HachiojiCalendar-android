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
    var district:Int = 0
    var season:Int = 0
    var cycleIndex:Int = 0
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
        if (dayOfMonth[position] != "") {
            for((index,i) in DistrictCycle().district[district][season][cycleIndex%2][position%7].withIndex()) {
                val garbage = GarbageType.valueOf(i)
                val name = CalendarHelper().garbagetypeString(garbage)
                val imageV = ImageView(context)
                if (name != null) {
                    imageV.setImageResource(name)
                    val viewParamsCenter = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    imageV.layoutParams = viewParamsCenter
                    holder.imageLayout.addView(imageV)
                    holder.garbage += i
                    viewParamsCenter.weight++
                }
            }

            if (position % 7 == 0) {
                cycleIndex += 1
            }
        }
    }

    override fun getItemCount(): Int {
        return dayOfMonth.size
    }

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String?, garbage: IntArray)
    }
}