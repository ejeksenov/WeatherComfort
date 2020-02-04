package kz.weather.domain.interaction.daily

import kz.weather.domain.base.BaseUseCase
import kz.weather.domain.model.Result
import kz.weather.domain.model.Weather5DaysForecast

interface GetWeather5DaysForecastUseCase: BaseUseCase<String, List<Weather5DaysForecast>> {
    override suspend fun invoke(param: String): Result<List<Weather5DaysForecast>>
}