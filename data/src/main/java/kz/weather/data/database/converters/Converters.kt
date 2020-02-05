package kz.weather.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kz.weather.data.networking.model.DayForecast

class Converters {
    private val gson = Gson()
    // Weather list converters

    @TypeConverter
    fun fromWeatherListToJson(list: MutableList<DayForecast>?): String {
        return list?.let { gson.toJson(it) } ?: ""
    }

    @TypeConverter
    fun fromJsonToWeatherList(jsonList: String): MutableList<DayForecast> {
        val listType = object : TypeToken<MutableList<DayForecast>>() {}.type
        return gson.fromJson(jsonList, listType)
    }
}