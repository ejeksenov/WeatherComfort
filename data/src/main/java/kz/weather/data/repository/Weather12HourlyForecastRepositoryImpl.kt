package kz.weather.data.repository

import kz.weather.data.database.dao.Weather12HourlyForecastDao
import kz.weather.data.database.model.Weather12HourlyForecastEntity
import kz.weather.data.networking.Weather12HourlyForecastApi
import kz.weather.data.networking.base.getData
import kz.weather.data.networking.base.getDataList
import kz.weather.domain.model.Result
import kz.weather.domain.model.Weather12HourlyForecast
import kz.weather.domain.repository.Weather12HourlyForecastRepository

class Weather12HourlyForecastRepositoryImpl(private val weather12HourlyForecastApi: Weather12HourlyForecastApi,
                                            private val weather12HourlyForecastDao: Weather12HourlyForecastDao): BaseRepository<Weather12HourlyForecast, Weather12HourlyForecastEntity>(), Weather12HourlyForecastRepository {
    override suspend fun getWeather12HourlyForecastList(locationKey: String): Result<List<Weather12HourlyForecast>> {
        return fetchDataList(
            apiDataProvider = {
                weather12HourlyForecastApi.getWeather12HourlyForecast(locationKey).getDataList(
                    fetchFromCacheAction = {weather12HourlyForecastDao.getWeatherData()},
                    cacheAction = {weather12HourlyForecastDao.saveWeatherData(it)}
                )
            },
            dbDataProvider = {weather12HourlyForecastDao.getWeatherData()}
        )
    }
}