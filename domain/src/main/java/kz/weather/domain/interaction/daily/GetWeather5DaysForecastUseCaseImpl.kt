package kz.weather.domain.interaction.daily

import kz.weather.domain.model.Result
import kz.weather.domain.model.Weather5DaysForecast
import kz.weather.domain.repository.Weather12HourlyForecastRepository
import kz.weather.domain.repository.Weather5DaysForecastRepository

class GetWeather5DaysForecastUseCaseImpl(private val weather5DaysForecastRepository: Weather5DaysForecastRepository): GetWeather5DaysForecastUseCase {
    override suspend fun invoke(param: String): Result<List<Weather5DaysForecast>> = weather5DaysForecastRepository.getWeather5DaysForecastList(param)
}