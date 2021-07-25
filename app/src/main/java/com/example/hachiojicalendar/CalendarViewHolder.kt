package com.example.hachiojicalendar

import android.view.View
import android.widget.ImageView
import com.example.hachiojicalendar.CalendarAdapter.OnItemListener
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.example.hachiojicalendar.R

class CalendarViewHolder(itemView: View, onItemListener: OnItemListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    @JvmField
    val dayOfMonth: TextView
    var garbage: IntArray = intArrayOf();
    val imageView:ImageView
    private val onItemListener: OnItemListener
    override fun onClick(view: View) {
        onItemListener.onItemClick(adapterPosition, dayOfMonth.text as String)
    }

    init {
        dayOfMonth = itemView.findViewById(R.id.cellDayText)
        imageView = itemView.findViewById(R.id.imageView)
        this.onItemListener = onItemListener
        itemView.setOnClickListener(this)
    }
}