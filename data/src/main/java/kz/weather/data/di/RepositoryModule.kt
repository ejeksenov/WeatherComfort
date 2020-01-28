package kz.weather.data.di

import kz.weather.data.common.utils.Connectivity
import kz.weather.data.common.utils.ConnectivityImpl
import kz.weather.data.repository.LocationInfoRepositoryImpl
import kz.weather.domain.repository.LocationInfoRepository

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<LocationInfoRepository> {  LocationInfoRepositoryImpl(get(), get()) }
    factory<Connectivity> { ConnectivityImpl(androidContext()) }
}