package kz.weather.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kz.weather.data.networking.model.Wind

class WindConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromWindToJson(wind: Wind?): String {
        return wind.let { gson.toJson(it) } ?: ""
    }

    @TypeConverter
    fun fromJsonToWind(json: String): Wind {
        val type = object : TypeToken<Wind>() {}.type
        return gson.fromJson(json, type)
    }


}