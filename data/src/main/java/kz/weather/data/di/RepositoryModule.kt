package kz.weather.data.di

import kz.weather.data.common.utils.Connectivity
import kz.weather.data.common.utils.ConnectivityImpl
import kz.weather.data.repository.LocationInfoRepositoryImpl
import kz.weather.data.repository.Weather12HourlyForecastRepositoryImpl
import kz.weather.data.repository.Weather5DaysForecastRepositoryImpl
import kz.weather.domain.repository.LocationInfoRepository
import kz.weather.domain.repository.Weather12HourlyForecastRepository
import kz.weather.domain.repository.Weather5DaysForecastRepository

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<LocationInfoRepository> {  LocationInfoRepositoryImpl(get(), get()) }
    factory<Weather12HourlyForecastRepository> {  Weather12HourlyForecastRepositoryImpl(get(), get()) }
    factory<Weather5DaysForecastRepository> {  Weather5DaysForecastRepositoryImpl(get(), get()) }
    factory<Connectivity> { ConnectivityImpl(androidContext()) }
}