package com.example.hachiojicalendar

import android.view.View
import com.example.hachiojicalendar.CalendarAdapter.OnItemListener
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.example.hachiojicalendar.R

class CalendarViewHolder(itemView: View, onItemListener: OnItemListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val dayOfMonth: TextView
    private val onItemListener: OnItemListener
    override fun onClick(view: View) {
        onItemListener.onItemClick(adapterPosition, dayOfMonth.text as String)
    }

    init {
        dayOfMonth = itemView.findViewById(R.id.cellDayText)
        this.onItemListener = onItemListener
        itemView.setOnClickListener(this)
    }
}