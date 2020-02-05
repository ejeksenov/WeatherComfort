package kz.weather.data.common.utils

import java.text.SimpleDateFormat
import java.util.*

object ChangeDateFormat {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ", Locale.ENGLISH)

    fun changeDateFormatToDate(dateTime: String): String {
        val outputFormat = SimpleDateFormat("MMM dd", Locale.ENGLISH)
        val date: Date = inputFormat.parse(dateTime)
        return outputFormat.format(date)
    }

    fun changeDateFormatToTime(dateTime: String): String {
        val outputFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val date: Date = inputFormat.parse(dateTime)
        return outputFormat.format(date)
    }
}