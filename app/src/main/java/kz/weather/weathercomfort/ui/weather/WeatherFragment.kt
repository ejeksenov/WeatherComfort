package kz.weather.weathercomfort.ui.weather


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kz.weather.domain.model.LocationInfo
import kz.weather.domain.model.Weather12HourlyForecast
import org.koin.androidx.viewmodel.ext.android.viewModel

import kz.weather.weathercomfort.R
import kz.weather.weathercomfort.databinding.WeatherFragmentBinding
import kz.weather.weathercomfort.ui.base.*
import kz.weather.weathercomfort.utils.snackbar
import kz.weather.weathercomfort.utils.subscribe

class WeatherFragment : BaseFragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var weatherFragmentBinding: WeatherFragmentBinding

    private val viewModel: WeatherViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weatherFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.weather_fragment, container, false)
        return weatherFragmentBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToData()
        viewModel.getLocationData()
    }

    private fun subscribeToData() {
        viewModel.viewState.subscribe(this, ::handleViewState)
        viewModel.viewData.subscribe(this, ::handleViewData)
    }

    private fun handleViewState(viewState: ViewState<LocationInfo>) {
        when (viewState) {
            is Loading -> showLoading(weatherFragmentBinding.weatherLoadingProgress)
            is Success -> getLocationKey(viewState.data)
            is Error -> handleError(viewState.error.localizedMessage!!)
            is NoInternetState -> showNoInternetError()
        }
    }

    private fun handleViewData(viewData: ViewState<List<Weather12HourlyForecast>>) {
        when(viewData) {
            is Loading -> showLoading(weatherFragmentBinding.weatherLoadingProgress)
            is Success -> showWeatherDataInfo(viewData.data)
            is Error -> handleError(viewData.error.localizedMessage!!)
            is NoInternetState -> showNoInternetError()
        }
    }

    private fun showWeatherDataInfo(dataList: List<Weather12HourlyForecast>) {
        hideLoading(weatherFragmentBinding.weatherLoadingProgress)
        var dataStr = ""
        for (item in dataList) {
            dataStr += " $item\n"
        }
        weatherFragmentBinding.tvAccuWeatherLocationData.text = dataStr
    }

    private fun handleError(error: String) {
        hideLoading(weatherFragmentBinding.weatherLoadingProgress)
        showError(error, weatherFragmentBinding.accuWeatherContainer)
    }

    private fun showNoInternetError() {
        hideLoading(weatherFragmentBinding.weatherLoadingProgress)
        snackbar(getString(R.string.no_internet_error_message), weatherFragmentBinding.accuWeatherContainer)
    }

    private fun getLocationKey(locationInfo: LocationInfo) {
        //hideLoading(weatherFragmentBinding.weatherLoadingProgress)
        val locationKey = locationInfo.key
        //weatherFragmentBinding.tvAccuWeatherLocationData.text = locationKey
        viewModel.getWeatherInfo(locationKey)
    }


}
