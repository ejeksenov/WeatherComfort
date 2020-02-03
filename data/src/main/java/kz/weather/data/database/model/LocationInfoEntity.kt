package kz.weather.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kz.weather.data.database.LOCATION_TABLE_NAME
import kz.weather.data.networking.base.DomainMapper
import kz.weather.domain.model.LocationInfo


@Entity(tableName = LOCATION_TABLE_NAME)
data class LocationInfoEntity(@PrimaryKey val key: String = "Key",
                              val type: String ?= "",
                              val localizedName: String ?= "",
                              val englishName: String ?= ""): DomainMapper<LocationInfo> {
    override fun mapToDomainModel() = LocationInfo(key, type ?: "", localizedName ?: "", englishName ?: "")
}