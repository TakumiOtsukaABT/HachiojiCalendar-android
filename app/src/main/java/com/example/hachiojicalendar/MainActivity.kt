package com.example.hachiojicalendar

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils.getActivity
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() , CalendarAdapter.OnItemListener {
    private lateinit var monthYearText:TextView
    private lateinit var calendarRecyclerView:RecyclerView;
    private lateinit var selectedDate:LocalDate
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

    override fun onItemClick(position: Int, dayText: String?) {
        if(dayText.equals(""))
        {
            var message:String = "Selected Date " + dayText + " " + monthFromDate(selectedDate)
            Toast.makeText(this,message,Toast.LENGTH_LONG)
        }
    }
}