package com.example.hachiojicalendar

import java.time.LocalDate

class DateWithSchedule(date: LocalDate, garbageArray: MutableList<Int>) {
    var dateComponent = LocalDate.now()
    var garbage = mutableListOf<Int>()
    init {
        dateComponent = date
        garbage = garbageArray
    }
}