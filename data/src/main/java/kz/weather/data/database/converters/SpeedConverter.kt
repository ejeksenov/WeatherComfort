package kz.weather.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kz.weather.data.networking.model.Speed

class SpeedConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromSpeedToJson(speed: Speed?): String {
        return speed?.let { gson.toJson(it) } ?: ""
    }

    @TypeConverter
    fun fromJsonToSpeed(json: String): Speed {
        val type = object : TypeToken<Speed>() {}.type
        return gson.fromJson(json, type)
    }

}