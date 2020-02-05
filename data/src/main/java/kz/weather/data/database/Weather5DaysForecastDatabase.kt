package kz.weather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kz.weather.data.database.converters.Converters
import kz.weather.data.database.converters.DayForecastConverter
import kz.weather.data.database.dao.Weather5DaysForecastDao
import kz.weather.data.database.model.Weather5DaysForecastEntity

@Database(entities = [Weather5DaysForecastEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class, DayForecastConverter::class)
abstract class Weather5DaysForecastDatabase: RoomDatabase() {
    abstract fun weather5DaysForecastDao(): Weather5DaysForecastDao
}