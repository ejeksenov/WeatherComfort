package kz.weather.comfort.di

import kz.weather.weathercomfort.ui.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { WeatherViewModel(get()) }
}