package kz.weather.data.networking.model

import com.google.gson.annotations.SerializedName
import kz.weather.data.database.model.LocationInfoEntity
import kz.weather.data.networking.base.RoomMapper


data class LocationInfoResponse(@SerializedName("Key") val key: String ?= "Key", @SerializedName("Type") val type: String ?= "", @SerializedName("LocalizedName") val localizedName: String ?= "", @SerializedName("EnglishName") val englishName: String ?= ""): RoomMapper<LocationInfoEntity> {
    override fun mapToRoomEntity() = LocationInfoEntity(key!!, type, localizedName, englishName)
}