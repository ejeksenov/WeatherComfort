package kz.weather.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kz.weather.data.networking.model.Temperature

class TemperatureConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromTemperatureToJson(temperature: Temperature?): String {
        return temperature.let { gson.toJson(it) } ?: ""
    }

    @TypeConverter
    fun fromJsonToTemperature(json: String): Temperature {
        val type = object : TypeToken<Temperature>() {}.type
        return gson.fromJson(json, type)
    }
}