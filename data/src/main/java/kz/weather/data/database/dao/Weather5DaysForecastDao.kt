package kz.weather.data.database.dao

import androidx.room.*
import kz.weather.data.database.WEATHER_DAILY_FORECAST_TABLE_NAME
import kz.weather.data.database.model.Weather5DaysForecastEntity

@Dao
interface Weather5DaysForecastDao {

    @Transaction
    suspend fun updateAndReturn(weather5DaysForecastEntity: Weather5DaysForecastEntity): Weather5DaysForecastEntity {
        saveWeatherData(weather5DaysForecastEntity)
        return getWeatherData()
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeatherData(weather5DaysForecastEntity: Weather5DaysForecastEntity)

    @Query("SELECT * FROM $WEATHER_DAILY_FORECAST_TABLE_NAME LIMIT 1")
    suspend fun getWeatherData(): Weather5DaysForecastEntity
}