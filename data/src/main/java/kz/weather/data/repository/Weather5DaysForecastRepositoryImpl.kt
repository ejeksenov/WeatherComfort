package kz.weather.data.repository

import kz.weather.data.database.dao.Weather5DaysForecastDao
import kz.weather.data.database.model.Weather5DaysForecastEntity
import kz.weather.data.networking.Weather5DaysForecastApi
import kz.weather.data.networking.base.getData
import kz.weather.domain.model.Result
import kz.weather.domain.model.Weather5DaysForecast
import kz.weather.domain.repository.Weather5DaysForecastRepository

class Weather5DaysForecastRepositoryImpl(private val weather5DaysForecastApi: Weather5DaysForecastApi,
                                         private val weather5DaysForecastDao: Weather5DaysForecastDao): BaseRepository<Weather5DaysForecast, Weather5DaysForecastEntity>(), Weather5DaysForecastRepository {
    override suspend fun getWeather5DaysForecastList(locationKey: String): Result<Weather5DaysForecast> {
        return fetchData(
            apiDataProvider = {
                weather5DaysForecastApi.getWeather5DaysForecast(locationKey).getData(
                    fetchFromCacheAction = {weather5DaysForecastDao.getWeatherData()},
                    cacheAction = {weather5DaysForecastDao.saveWeatherData(it)}
                )
            },
            dbDataProvider = {weather5DaysForecastDao.getWeatherData()}
        )
    }

}