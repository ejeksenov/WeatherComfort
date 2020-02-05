package kz.weather.data.networking.model

import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import kz.weather.data.database.converters.DayForecastConverter
import kz.weather.data.database.model.Weather5DaysForecastEntity
import kz.weather.data.networking.base.RoomMapper
import kz.weather.domain.repository.Weather5DaysForecastRepository

data class Weather5DayForecastResponse(@SerializedName("DailyForecasts") val dailyForeCasts: List<DayForecast>) :
    RoomMapper<Weather5DaysForecastEntity> {
    override fun mapToRoomEntity(): Weather5DaysForecastEntity {
        return Weather5DaysForecastEntity(0,dailyForeCasts)
    }

}
@TypeConverters(DayForecastConverter::class)
data class DayForecast(@SerializedName("Date") val date: String, @SerializedName("Sun") val sun: Sun,
                       @SerializedName("Temperature") val temperature: DayTemperature, @SerializedName("Day") val day: Day,
                       @SerializedName("Night") val night: Day, @SerializedName("MobileLink") val mobileLink: String)

data class Sun(@SerializedName("Rise") val rise: String, @SerializedName("Set") val set: String)

data class DayTemperature(@SerializedName("Maximum") val maximum: Maximum, @SerializedName("Minimum") val minimum: Maximum)

data class Maximum(@SerializedName("Value") val value: Double, @SerializedName("Unit") val unit: String)

data class Day(@SerializedName("Icon") val icon: Int)