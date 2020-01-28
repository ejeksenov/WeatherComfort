package kz.weather.weathercomfort.ui.weather


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kz.weather.domain.model.LocationInfo
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

    private val viewModel: WeatherViewModel by viewModel()
    private lateinit var weatherFragmentBinding: WeatherFragmentBinding

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
    }

    private fun handleViewState(viewState: ViewState<LocationInfo>) {
        when (viewState) {
            is Loading -> showLoading(weatherFragmentBinding.weatherLoadingProgress)
            is Success -> showWeatherData(viewState.data)
            is Error -> handleError(viewState.error.localizedMessage!!)
            is NoInternetState -> showNoInternetError()
        }
    }

    private fun handleError(error: String) {
        hideLoading(weatherFragmentBinding.weatherLoadingProgress)
        showError(error, weatherFragmentBinding.accuWeatherContainer)
    }

    private fun showNoInternetError() {
        hideLoading(weatherFragmentBinding.weatherLoadingProgress)
        snackbar(getString(R.string.no_internet_error_message), weatherFragmentBinding.accuWeatherContainer)
    }

    private fun showWeatherData(locationInfo: LocationInfo) {
        hideLoading(weatherFragmentBinding.weatherLoadingProgress)
        val locationDataStr = "key: ${locationInfo.key}\ntype: ${locationInfo.type}\nlocalizedName: ${locationInfo.localizedName}\nenglishName: ${locationInfo.englishName}"
        weatherFragmentBinding.tvAccuWeatherLocationData.text = locationDataStr
    }


}
