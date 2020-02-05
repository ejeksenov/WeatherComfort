package kz.weather.data.di

import androidx.room.Room
import kz.weather.data.database.LocationDatabase
import kz.weather.data.database.Weather12HourlyForecastDatabase
import kz.weather.data.database.Weather5DaysForecastDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val LOCATION_DATA_DB = "location_data_database"
private const val WEATHER_HOURLY_INFO_DB = "weather_hourly_info_database"
private const val WEATHER_DAILY_INFO_DB = "weather_daily_info_database"

val databaseModule = module {
    single {
        //TODO remove fallbackToDestructiveMigration when this goes to production
        Room.databaseBuilder(androidContext(), LocationDatabase::class.java, LOCATION_DATA_DB).fallbackToDestructiveMigration().build()
    }
    single {
        Room.databaseBuilder(androidContext(), Weather12HourlyForecastDatabase::class.java, WEATHER_HOURLY_INFO_DB).fallbackToDestructiveMigration().build()
    }
    single {
        Room.databaseBuilder(androidContext(), Weather5DaysForecastDatabase::class.java, WEATHER_DAILY_INFO_DB).fallbackToDestructiveMigration().build()
    }
    factory { get<LocationDatabase>().locationDataDao() }
    factory { get<Weather12HourlyForecastDatabase>().weather12HourlyForecastDao() }
    factory { get<Weather5DaysForecastDatabase>().weather5DaysForecastDao() }
}