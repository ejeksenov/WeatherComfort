package kz.weather.data.database.dao

import androidx.room.*
import kz.weather.data.database.LOCATION_TABLE_NAME
import kz.weather.data.database.model.LocationInfoEntity

@Dao
interface LocationInfoDao {

    @Transaction
    suspend fun updateLocationAndReturn(locationInfoEntity: LocationInfoEntity): LocationInfoEntity {
        saveLocationData(locationInfoEntity)
        return getLocationInfo()
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocationData(locationDataEntity: LocationInfoEntity)

    @Query("SELECT * FROM $LOCATION_TABLE_NAME LIMIT 1")
    suspend fun getLocationInfo(): LocationInfoEntity

}