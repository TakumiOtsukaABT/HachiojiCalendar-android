package com.example.hachiojicalendar

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() , CalendarAdapter.OnItemListener {
    private lateinit var monthYearText:TextView
    private lateinit var calendarRecyclerView:RecyclerView;
    private lateinit var selectedDate:LocalDate
    private lateinit var descriptionText:TextView
    private lateinit var districtButton:Button
    private var districtIndex=0;
    val districtList = arrayOf("地区1", "地区2","地区3","地区4","地区5","地区6","地区7","地区8","地区9",
        "地区10","地区11","地区12","地区13","地区14","地区15","地区16","地区17","地区18","地区19")

    lateinit var mAdView : AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = getPreferences(MODE_PRIVATE)
        districtIndex = data.getInt("District", 0)
        setContentView(R.layout.activity_main)
        initWidget()
        selectedDate = LocalDate.now()
        districtButton.text = districtList[districtIndex]
        setMonthView()

        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
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

    private fun storeDateWithSchedule() {
        CalendarHelper.sched.clear()
        var array = ArrayList<DateWithSchedule>()
        for (i in 0..41) {
            val cell = calendarRecyclerView.findViewHolderForAdapterPosition(i) as CalendarViewHolder
            if (cell.garbage.isNotEmpty()) {
                val dateNow = LocalDate.now()
                val intConvert = Integer.parseInt(cell.dayOfMonth.text.toString())
                val localDate = LocalDate.of(dateNow.year,selectedDate.month,intConvert)
                var dateWithSchedule = DateWithSchedule(localDate,cell.garbage)
                array.add(dateWithSchedule)
            }
        }
        CalendarHelper.sched = array
    }

    private fun initWidget() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.MonthYearTV)
        descriptionText = findViewById(R.id.description_textview)
        districtButton = findViewById(R.id.district_button)
        mAdView = findViewById(R.id.adView)
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
        val data = getPreferences(MODE_PRIVATE)
        val editor = data.edit()
        editor.putInt("District", districtIndex)
        editor.apply()
    }

    fun openSettings(view:View) {
        storeDateWithSchedule()
        val intent = Intent(this,SettingsActivity::class.java)
        startActivity(intent)
    }

    override fun onItemClick(position: Int, dayText: String?, garbage: MutableList<Int>) {
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