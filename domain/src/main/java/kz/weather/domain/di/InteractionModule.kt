package kz.weather.domain.di


import kz.weather.domain.interaction.daily.GetWeather5DaysForecastUseCase
import kz.weather.domain.interaction.daily.GetWeather5DaysForecastUseCaseImpl
import kz.weather.domain.interaction.location.GetLocationInfoUseCase
import kz.weather.domain.interaction.location.GetLocationInfoUseCaseImpl
import kz.weather.domain.interaction.hourly.GetWeather12HourlyForecastUseCase
import kz.weather.domain.interaction.hourly.GetWeather12HourlyForecastUseCaseImpl
import org.koin.dsl.module

val interactionModule = module {
    factory<GetLocationInfoUseCase> {
        GetLocationInfoUseCaseImpl(
            get()
        )
    }
    factory<GetWeather12HourlyForecastUseCase> {
        GetWeather12HourlyForecastUseCaseImpl(
            get()
        )
    }
    factory<GetWeather5DaysForecastUseCase> {
        GetWeather5DaysForecastUseCaseImpl(
            get()
        )
    }
}