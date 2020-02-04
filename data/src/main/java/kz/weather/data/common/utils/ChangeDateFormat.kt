package kz.weather.data.common.utils

import java.text.SimpleDateFormat
import java.util.*

object ChangeDateFormat {
    fun changeDateFormat(dateTime: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'V'", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        val date: Date = inputFormat.parse(dateTime)
        return outputFormat.format(date)
    }
}