package kz.weather.weathercomfort.ui.weather

import kz.weather.domain.interaction.location.GetLocationInfoUseCase
import kz.weather.domain.interaction.weather.GetWeather12HourlyForecastUseCase
import kz.weather.domain.model.*
import kz.weather.weathercomfort.ui.base.BaseViewModel
import kz.weather.weathercomfort.ui.base.Error
import kz.weather.weathercomfort.ui.base.Success
import kz.weather.weathercomfort.utils.DEFAULT_CITY_LOCATION

class WeatherViewModel(private val getLocationInfoUseCase: GetLocationInfoUseCase,
                       private val getWeather12HourlyForecastUseCase: GetWeather12HourlyForecastUseCase) : BaseViewModel<LocationInfo, List<Weather12HourlyForecast>>(){

    fun getLocationData(location: String = DEFAULT_CITY_LOCATION) = executeUseCase {
        getLocationInfoUseCase(location).onSuccess {
            _viewState.value = Success(it)
        }.onFailure {
            _viewState.value = Error(it.throwable)
        }
    }

    fun getWeatherInfo(locationKey: String) = executeUseCase {
        getWeather12HourlyForecastUseCase(locationKey).onSuccess {
            _viewData.value = Success(it)
        }.onFailure {
            _viewData.value = Error(it.throwable)
        }
    }



}
