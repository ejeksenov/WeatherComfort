package kz.weather.data.database.dao

import androidx.room.*
import kz.weather.data.database.WEATHER_HOURLY_FORECAST_TABLE_NAME
import kz.weather.data.database.model.Weather12HourlyForecastEntity

@Dao
interface Weather12HourlyForecastDao {

    @Transaction
    suspend fun updateWeatherAndReturn(weather12HourlyForecastEntityList: List<Weather12HourlyForecastEntity>): List<Weather12HourlyForecastEntity>{
        saveWeatherData(weather12HourlyForecastEntityList)
        return getWeatherData()
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeatherData(weather12HourlyForecastEntityList: List<Weather12HourlyForecastEntity>)

    @Query("SELECT * FROM $WEATHER_HOURLY_FORECAST_TABLE_NAME LIMIT 1")
    suspend fun getWeatherData(): List<Weather12HourlyForecastEntity>

}