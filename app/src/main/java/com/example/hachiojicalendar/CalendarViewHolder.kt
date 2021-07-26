package com.example.hachiojicalendar

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.hachiojicalendar.CalendarAdapter.OnItemListener
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.example.hachiojicalendar.R

class CalendarViewHolder(itemView: View, onItemListener: OnItemListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    @JvmField
    val dayOfMonth: TextView
    var garbage: IntArray = intArrayOf();
    val imageLayout:LinearLayout
    private val onItemListener: OnItemListener
    override fun onClick(view: View) {
        onItemListener.onItemClick(adapterPosition, dayOfMonth.text as String)
    }

    init {
        dayOfMonth = itemView.findViewById(R.id.cellDayText)
        this.onItemListener = onItemListener
        imageLayout = itemView.findViewById(R.id.imageLayout)
        itemView.setOnClickListener(this)
    }
}