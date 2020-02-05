package kz.weather.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kz.weather.data.common.utils.ChangeDateFormat.changeDateFormatToDate
import kz.weather.data.common.utils.ChangeDateFormat.changeDateFormatToTime
import kz.weather.data.common.utils.ChangeImageFormat.getImageFormat
import kz.weather.data.database.WEATHER_DAILY_FORECAST_TABLE_NAME
import kz.weather.data.database.converters.Converters
import kz.weather.data.database.converters.DayForecastConverter
import kz.weather.data.networking.base.DomainMapper
import kz.weather.data.networking.model.Day
import kz.weather.data.networking.model.DayForecast
import kz.weather.domain.model.Weather5DaysForecast
import kotlin.math.roundToInt

@Entity(tableName = WEATHER_DAILY_FORECAST_TABLE_NAME)
data class Weather5DaysForecastEntity(@PrimaryKey(autoGenerate = true) val id: Int, @TypeConverters(Converters::class, DayForecastConverter::class) val dailyForecasts: List<DayForecast>): DomainMapper<Weather5DaysForecast> {
    override fun mapToDomainModel(): Weather5DaysForecast {
        val list: List<kz.weather.domain.model.DayForecast> = dailyForecasts.map {
            kz.weather.domain.model.DayForecast(
                date =  changeDateFormatToDate(it.date),
                sunRise = changeDateFormatToTime(it.sun.rise),
                sunSet = changeDateFormatToTime(it.sun.set),
                temperatureMaximum = "${it.temperature.maximum.value.roundToInt()}°",
                temperatureMinimum = "${it.temperature.minimum.value.roundToInt()}°",
                dayIcon =  getImageFormat(it.day.icon),
                nightIcon = getImageFormat(it.night.icon),
                mobileLink = it.mobileLink
            )
        }
        return Weather5DaysForecast(list)
    }



}