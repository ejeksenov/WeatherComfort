package kz.weather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kz.weather.data.database.dao.LocationInfoDao
import kz.weather.data.database.model.LocationInfoEntity


@Database(entities = [LocationInfoEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LocationDatabase: RoomDatabase() {
    abstract fun locationDataDao(): LocationInfoDao
}