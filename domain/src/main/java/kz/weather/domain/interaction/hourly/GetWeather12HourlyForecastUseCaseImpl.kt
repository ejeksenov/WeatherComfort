package kz.weather.domain.interaction.hourly

import kz.weather.domain.model.Result
import kz.weather.domain.model.Weather12HourlyForecast
import kz.weather.domain.repository.Weather12HourlyForecastRepository

class GetWeather12HourlyForecastUseCaseImpl(private val weather12HourlyForecastRepository: Weather12HourlyForecastRepository): GetWeather12HourlyForecastUseCase {
    override suspend fun invoke(param: String): Result<List<Weather12HourlyForecast>> = weather12HourlyForecastRepository.getWeather12HourlyForecastList(param)
}