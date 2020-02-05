package kz.weather.domain.interaction.hourly

import kz.weather.domain.base.BaseUseCase
import kz.weather.domain.model.Result
import kz.weather.domain.model.Weather12HourlyForecast

interface GetWeather12HourlyForecastUseCase: BaseUseCase<String, List<Weather12HourlyForecast>> {
    override suspend operator fun invoke(param: String): Result<List<Weather12HourlyForecast>>
}