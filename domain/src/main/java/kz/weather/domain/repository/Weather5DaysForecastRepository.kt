package kz.weather.domain.repository

import kz.weather.domain.model.Result
import kz.weather.domain.model.Weather5DaysForecast

interface Weather5DaysForecastRepository {
    suspend fun getWeather5DaysForecastList(locationKey: String): Result<List<Weather5DaysForecast>>
}