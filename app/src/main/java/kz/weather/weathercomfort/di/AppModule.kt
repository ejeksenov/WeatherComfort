package kz.weather.weathercomfort.di

import kz.weather.data.common.coroutine.CoroutineContextProvider
import kz.weather.weathercomfort.ui.MainActivity
import org.koin.dsl.module

val appModule = module {
    single { CoroutineContextProvider() }
}