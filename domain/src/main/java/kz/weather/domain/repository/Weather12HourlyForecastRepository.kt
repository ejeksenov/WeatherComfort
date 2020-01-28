package kz.weather.domain.repository

import kz.weather.domain.model.Result
import kz.weather.domain.model.Weather12HourlyForecast

interface Weather12HourlyForecastRepository {
    suspend fun getWeather12HourlyForecastList(locationKey: String): Result<MutableList<Weather12HourlyForecast>>
}