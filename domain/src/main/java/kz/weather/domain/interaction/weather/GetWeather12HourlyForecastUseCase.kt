package kz.weather.domain.interaction.weather

import kz.weather.domain.base.BaseUseCase
import kz.weather.domain.model.Result
import kz.weather.domain.model.Weather12HourlyForecast

interface GetWeather12HourlyForecastUseCase: BaseUseCase<String, MutableList<Weather12HourlyForecast>> {
    override suspend operator fun invoke(param: String): Result<MutableList<Weather12HourlyForecast>>
}