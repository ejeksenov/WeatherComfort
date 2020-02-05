package kz.weather.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kz.weather.data.networking.model.DayForecast

class DayForecastConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromDayForecastToJson(dayForecast: DayForecast?): String {
        return dayForecast?.let { gson.toJson(it) } ?: ""
    }

    @TypeConverter
    fun fromJsonToDirection(json: String): DayForecast {
        val type = object : TypeToken<DayForecast>() {}.type
        return gson.fromJson(json, type)
    }
}