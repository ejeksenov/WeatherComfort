package kz.weather.data.repository


import kz.weather.data.database.dao.LocationInfoDao
import kz.weather.data.database.model.LocationInfoEntity
import kz.weather.data.networking.LocationInfoApi
import kz.weather.data.networking.base.getData
import kz.weather.domain.model.LocationInfo
import kz.weather.domain.model.Result
import kz.weather.domain.repository.LocationInfoRepository

class LocationInfoRepositoryImpl(private val locationDataApi: LocationInfoApi,
                                 private val locationDataDao: LocationInfoDao): BaseRepository<LocationInfo, LocationInfoEntity>(), LocationInfoRepository {

    override suspend fun getLocationInfo(location: String): Result<LocationInfo> {
        return fetchData(
            apiDataProvider = {
                locationDataApi.getLocationData(location = location).getData(
                    fetchFromCacheAction = { locationDataDao.getLocationInfo()},
                    cacheAction = {locationDataDao.saveLocationData(it)}
                )
            },
            dbDataProvider = {locationDataDao.getLocationInfo()}
        )
    }


}