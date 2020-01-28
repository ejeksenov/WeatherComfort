package kz.weather.domain.repository

import kz.weather.domain.model.LocationInfo
import kz.weather.domain.model.Result

interface LocationInfoRepository {
    suspend fun getLocationInfo(location: String): Result<LocationInfo>
}