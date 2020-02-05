package kz.weather.weathercomfort.ui.weather

import androidx.lifecycle.MutableLiveData
import kz.weather.domain.interaction.daily.GetWeather5DaysForecastUseCase
import kz.weather.domain.interaction.location.GetLocationInfoUseCase
import kz.weather.domain.interaction.hourly.GetWeather12HourlyForecastUseCase
import kz.weather.domain.model.*
import kz.weather.weathercomfort.ui.base.BaseViewModel
import kz.weather.weathercomfort.ui.base.Error
import kz.weather.weathercomfort.ui.base.Success
import kz.weather.weathercomfort.utils.DEFAULT_CITY_LOCATION
import java.util.*
import kotlin.collections.ArrayList

class WeatherViewModel(private val getLocationInfoUseCase: GetLocationInfoUseCase,
                       private val getWeather12HourlyForecastUseCase: GetWeather12HourlyForecastUseCase,
                       private val getWeather5DaysForecastUseCase: GetWeather5DaysForecastUseCase) : BaseViewModel<LocationInfo, List<Weather12HourlyForecast>, Weather5DaysForecast>(){

    fun getLocationData(location: String = DEFAULT_CITY_LOCATION) = executeUseCase {
        getLocationInfoUseCase(location).onSuccess {
            _viewState.value = Success(it)
        }.onFailure {
            _viewState.value = Error(it.throwable)
        }
    }

    fun getWeatherHourlyInfo(locationKey: String) = executeUseCase {
        getWeather12HourlyForecastUseCase(locationKey).onSuccess {
            _viewData.value = Success(it)
        }.onFailure {
            _viewData.value = Error(it.throwable)
        }
    }

    fun getWeatherDailyInfo(locationKey: String) = executeUseCase {
        getWeather5DaysForecastUseCase(locationKey).onSuccess {
            _viewDataInfo.value = Success(it)
        }.onFailure {
            _viewDataInfo.value = Error(it.throwable)
        }
    }




}
