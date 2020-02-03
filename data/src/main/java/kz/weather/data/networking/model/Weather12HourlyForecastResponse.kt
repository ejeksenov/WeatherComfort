package kz.weather.data.networking.model

import androidx.room.TypeConverter
import com.google.gson.annotations.SerializedName
import kz.weather.data.database.model.Weather12HourlyForecastEntity
import kz.weather.data.networking.base.RoomMapper

data class Weather12HourlyForecastResponse(@SerializedName("DateTime") val dateTime: String, @SerializedName("WeatherIcon") val weatherIcon : Int ?= 0, @SerializedName("IconPhrase") val iconPhrase: String ?= "",
                                           @SerializedName("Temperature") val temperature: Temperature, @SerializedName("RealFeelTemperature") val realFeelTemperature: Temperature, @SerializedName("Wind") val wind: Wind,
                                           @SerializedName("RelativeHumidity") val relativeHumidity: Int, @SerializedName("UVIndexText") val uviIndexText: String, @SerializedName("MobileLink") val mobileLink: String): RoomMapper<Weather12HourlyForecastEntity> {
    override fun mapToRoomEntity(): Weather12HourlyForecastEntity {
        return Weather12HourlyForecastEntity(dateTime, weatherIcon, iconPhrase, temperature, realFeelTemperature, wind, relativeHumidity, uviIndexText, mobileLink)
    }
}

data class Temperature(@SerializedName("Value") val value: Double, @SerializedName("Unit") val unit: String, @SerializedName("UnitType") val unitType: Int)
data class Wind(@SerializedName("Speed") val speed: Speed, @SerializedName("Direction") val direction: Direction)
data class Speed(@SerializedName("Value") val value: Double, @SerializedName("Unit") val unit: String, @SerializedName("UnitType") val unitType: Int)
data class Direction(@SerializedName("Degrees") val degrees: Int, @SerializedName("Localized") val localized: String, @SerializedName("English") val english: String)