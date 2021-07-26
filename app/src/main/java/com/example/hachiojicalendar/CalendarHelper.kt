package com.example.hachiojicalendar

import java.time.LocalDate

class CalendarHelper {
    fun garbagetypeString(type: GarbageType): Int? {
        when (type) {
            GarbageType.non->{return null}
            GarbageType.burn->{return R.drawable.gomibukuro_blue}
            GarbageType.nonburn->{return R.drawable.gomibukuro_yellow}
            GarbageType.bottle->{return R.drawable.bottle}
            GarbageType.plastic->{return R.drawable.plastic}
            GarbageType.bin->{return R.drawable.bin}
            GarbageType.can->{return R.drawable.can}
            GarbageType.box->{return R.drawable.box}
            GarbageType.cloth->{return R.drawable.cloth}
            GarbageType.yuugai->{return R.drawable.yuugai}
            GarbageType.newspaper->{return R.drawable.newspaper}
            GarbageType.magazine->{return R.drawable.magazine}
        }
    }

    fun getSeason(date:LocalDate): Int {
        val month = date.month.value
        return (month-1)/3
    }
}