package kz.weather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kz.weather.data.database.converters.Converters
import kz.weather.data.database.converters.TemperatureConverter
import kz.weather.data.database.converters.WindConverter
import kz.weather.data.database.dao.Weather12HourlyForecastDao
import kz.weather.data.database.model.Weather12HourlyForecastEntity

@Database(entities = [Weather12HourlyForecastEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class, TemperatureConverter::class, WindConverter::class)
abstract class Weather12HourlyForecastDatabase: RoomDatabase() {
    abstract fun weather12HourlyForecastDao(): Weather12HourlyForecastDao
}