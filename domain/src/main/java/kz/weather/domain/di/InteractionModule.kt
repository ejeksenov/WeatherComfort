package kz.weather.domain.di


import kz.weather.domain.interaction.GetLocationInfoUseCase
import kz.weather.domain.interaction.GetLocationInfoUseCaseImpl
import org.koin.dsl.module

val interactionModule = module {
    factory<GetLocationInfoUseCase> {GetLocationInfoUseCaseImpl(get())}
}