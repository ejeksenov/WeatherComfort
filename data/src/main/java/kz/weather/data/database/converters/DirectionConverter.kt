package kz.weather.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kz.weather.data.networking.model.Direction

class DirectionConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromDirectionToJson(direction: Direction?): String {
        return direction?.let { gson.toJson(it) } ?: ""
    }

    @TypeConverter
    fun fromJsonToDirection(json: String): Direction {
        val type = object : TypeToken<Direction>() {}.type
        return gson.fromJson(json, type)
    }
}