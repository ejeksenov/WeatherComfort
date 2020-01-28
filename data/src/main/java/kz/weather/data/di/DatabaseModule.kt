package kz.weather.data.di

import androidx.room.Room
import kz.weather.data.database.LocationDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val LOCATION_DATA_DB = "location_data_database"
private const val WEATHER_INFO_DB = "weather_info_database"

val databaseModule = module {
    single {
        //TODO remove fallbackToDestructiveMigration when this goes to production
        Room.databaseBuilder(androidContext(), LocationDatabase::class.java, LOCATION_DATA_DB).fallbackToDestructiveMigration().build()
    }
    factory { get<LocationDatabase>().locationDataDao() }
}