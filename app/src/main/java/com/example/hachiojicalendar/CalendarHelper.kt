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

    fun getGarbageTypeJapanese(garbage: GarbageType) :String? {
        when (garbage) {
            GarbageType.non->{return null}
            GarbageType.burn->{return "可燃ゴミ"}
            GarbageType.nonburn->{return "不燃ゴミ"}
            GarbageType.bottle->{return "ペットボトル"}
            GarbageType.plastic->{return "プラスチック"}
            GarbageType.bin->{return "ビン"}
            GarbageType.can->{return "缶"}
            GarbageType.box->{return "段ボール"}
            GarbageType.cloth->{return "布"}
            GarbageType.yuugai->{return "有害ゴミ（スプレー缶など）"}
            GarbageType.newspaper->{return "新聞紙"}
            GarbageType.magazine->{return "雑誌・雑紙・紙パック"}
        }
    }

    fun getSeason(date:LocalDate): Int {
        val month = date.month.value
        return (month-1)/3
    }
}