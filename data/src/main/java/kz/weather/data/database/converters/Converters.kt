package kz.weather.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()
    // Weather list converters

    @TypeConverter
    fun fromWeatherListToJson(list: MutableList<Any>?): String {
        return list?.let { gson.toJson(it) } ?: ""
    }

    @TypeConverter
    fun fromJsonToWeatherList(jsonList: String): MutableList<Any> {
        val listType = object : TypeToken<MutableList<Any>>() {}.type
        return gson.fromJson(jsonList, listType)
    }
}