package kz.weather.weathercomfort.ui.weather

import androidx.lifecycle.ViewModel
import kz.weather.domain.interaction.GetLocationInfoUseCase
import kz.weather.domain.model.LocationInfo
import kz.weather.domain.model.onFailure
import kz.weather.domain.model.onSuccess
import kz.weather.weathercomfort.ui.base.BaseViewModel
import kz.weather.weathercomfort.ui.base.Error
import kz.weather.weathercomfort.ui.base.Success
import kz.weather.weathercomfort.utils.DEFAULT_CITY_LOCATION

class WeatherViewModel(private val getLocationInfoUseCase: GetLocationInfoUseCase) : BaseViewModel<LocationInfo>(){

    fun getLocationData(location: String = DEFAULT_CITY_LOCATION) = executeUseCase {
        getLocationInfoUseCase(location).onSuccess {
            _viewState.value = Success(it)
        }.onFailure {
            _viewState.value = Error(it.throwable)
        }
    }
}
