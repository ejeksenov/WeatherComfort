package kz.weather.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kz.weather.data.common.utils.ChangeDateFormat.changeDateFormatToTime
import kz.weather.data.common.utils.ChangeImageFormat.getImageFormat
import kz.weather.data.database.WEATHER_HOURLY_FORECAST_TABLE_NAME
import kz.weather.data.database.converters.TemperatureConverter
import kz.weather.data.database.converters.WindConverter
import kz.weather.data.networking.base.DomainMapper
import kz.weather.data.networking.model.Temperature
import kz.weather.data.networking.model.Wind
import kz.weather.domain.model.Weather12HourlyForecast
import kotlin.math.roundToInt

@Entity(tableName = WEATHER_HOURLY_FORECAST_TABLE_NAME)
data class Weather12HourlyForecastEntity(
    @PrimaryKey val dateTime: String, val weatherIcon : Int ?= 0, val iconPhrase: String ?= "",
    @TypeConverters(TemperatureConverter::class) val temperature: Temperature, @TypeConverters(TemperatureConverter::class) val realFeelTemperature: Temperature, @TypeConverters(WindConverter::class) val wind: Wind,
    val relativeHumidity: Int ?= 0, val uviIndexText: String ?= "", val mobileLink: String ?= ""
): DomainMapper<Weather12HourlyForecast> {
    override fun mapToDomainModel(): Weather12HourlyForecast {
        return Weather12HourlyForecast(
            changeDateFormatToTime(dateTime), getImageFormat(weatherIcon ?: 0), iconPhrase ?: "",
            "${temperature.value.roundToInt()}°", "${realFeelTemperature.value.roundToInt()}°", "${wind.speed.value} ${wind.speed.unit}",
            "$relativeHumidity%", uviIndexText ?: "", mobileLink ?: "")
    }
}