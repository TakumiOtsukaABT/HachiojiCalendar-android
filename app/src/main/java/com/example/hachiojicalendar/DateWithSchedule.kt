package com.example.hachiojicalendar

import java.time.LocalDate
import java.util.*

class DateWithSchedule constructor(date: LocalDate,garbageArray:IntArray) {
    var dateComponent = LocalDate.now()
    var garbage: IntArray = intArrayOf()
    init {
        dateComponent = date
        garbage = garbageArray
    }
}