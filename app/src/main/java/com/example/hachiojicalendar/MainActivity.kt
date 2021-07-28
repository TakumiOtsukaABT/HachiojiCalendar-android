package com.example.hachiojicalendar

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() , CalendarAdapter.OnItemListener {
    private lateinit var monthYearText:TextView
    private lateinit var calendarRecyclerView:RecyclerView;
    private lateinit var selectedDate:LocalDate
    private lateinit var descriptionText:TextView
    private var districtIndex=0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWidget()
        selectedDate = LocalDate.now()
        setMonthView()
    }

    private fun setMonthView() {
        monthYearText.setText(selectedDate.month.value.toString() +"月")
        var daysInMonth:ArrayList<String> = daysInMonthArray(selectedDate)
        var calendarAdapter:CalendarAdapter = CalendarAdapter(daysInMonth, this)
        calendarAdapter.season = CalendarHelper().getSeason(selectedDate)
        calendarAdapter.district = districtIndex
        var layoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter

    }

    fun updateMonthView() {
        setMonthView()
        println("called setmonthview")
    }

    private fun daysInMonthArray(date: LocalDate): ArrayList<String> {
        var daysInMonthArray = ArrayList<String>()
        var yearMonth = YearMonth.from(date)

        var daysInMonth = yearMonth.lengthOfMonth()
        var firstOfMonth = selectedDate.withDayOfMonth(1)
        var dayOfWeek = firstOfMonth.dayOfWeek.getValue()

        for (i in 1..42) {
            if(i<=dayOfWeek || i>daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("")
            }
            else
            {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }


        }
        return daysInMonthArray

    }

    private fun monthFromDate(date:LocalDate): String? {
        var formatter = DateTimeFormatter.ofPattern("MMMM")
        return date.format(formatter)
    }

    private fun initWidget() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.MonthYearTV)
        descriptionText = findViewById(R.id.description_textview)
    }

    fun previousMonthAction(view: View) {
        if (selectedDate.minusMonths(1).month.value!=12) {
            selectedDate = selectedDate.minusMonths(1)
            setMonthView()
        }
    }
    fun nextMonthAction(view: View) {
        if (selectedDate.plusMonths(1).month.value!=1) {
            selectedDate = selectedDate.plusMonths(1)
            setMonthView()
        }
    }

    fun setDistrict(view:View) {
        val districtDialog = DistrictDialog()
        districtDialog.districtButton = findViewById(R.id.district_button)
        districtDialog.show(supportFragmentManager, "district_tag")
    }

    fun setDistrictIndex(index:Int) {
        districtIndex = index
    }

    fun openSettings(view:View) {
        val intent = Intent(this,SettingsActivity::class.java)
        startActivity(intent)
    }

    override fun onItemClick(position: Int, dayText: String?, garbage: IntArray) {
        descriptionText.text = ""
        if (garbage != null){
            for (i in garbage) {
                val garbageType = GarbageType.valueOf(i)
                val garbageJapanese = CalendarHelper().getGarbageTypeJapanese(garbageType)
                descriptionText.text = descriptionText.text.toString() + garbageJapanese + "\n"
            }
        }
        for (i in 0..41) {
            val cell = calendarRecyclerView.findViewHolderForAdapterPosition(i) as CalendarViewHolder
            cell.cellLayout.setBackgroundColor(Color.WHITE)
        }
        val cell = calendarRecyclerView.findViewHolderForAdapterPosition(position) as CalendarViewHolder
        cell.cellLayout.setBackgroundColor(Color.YELLOW)
    }
}