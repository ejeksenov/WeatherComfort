package kz.weather.weathercomfort.ui.weather


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kz.weather.domain.model.LocationInfo
import kz.weather.domain.model.Weather12HourlyForecast
import kz.weather.domain.model.Weather5DaysForecast
import org.koin.androidx.viewmodel.ext.android.viewModel

import kz.weather.weathercomfort.R
import kz.weather.weathercomfort.databinding.WeatherFragmentBinding
import kz.weather.weathercomfort.ui.base.*
import kz.weather.weathercomfort.utils.*

class WeatherFragment : BaseFragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var weatherFragmentBinding: WeatherFragmentBinding

    private val viewModel: WeatherViewModel by viewModel()

    private lateinit var hourlyForecastAdapter: WeatherHourlyForecastAdapter
    private lateinit var dailyForecastAdapter: WeatherDailyForecastAdapter
    private lateinit var weatherDetailedInfoAdapter: WeatherDetailedInfoAdapter

    private val weatherDetailedInfoHashMap: HashMap<String, String> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weatherFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.weather_fragment, container, false)

        onSetHourlyForecastAdapter()
        onSetDailyForecastAdapter()
        onSetWeatherDetailedInfoAdapter()

        return weatherFragmentBinding.root
    }

    private fun onSetWeatherDetailedInfoAdapter() {
        weatherFragmentBinding.rvDetailedWeatherInfoList.addItemDecoration(DividerItemDecoration(
            context!!,
            RecyclerView.VERTICAL
        ))
        weatherDetailedInfoAdapter = WeatherDetailedInfoAdapter()
        weatherFragmentBinding.rvDetailedWeatherInfoList.adapter = weatherDetailedInfoAdapter
    }

    private fun onSetDailyForecastAdapter() {
        weatherFragmentBinding.rvDailyForecastList.addItemDecoration(
            DividerItemDecoration(
                context!!,
                RecyclerView.HORIZONTAL
            )
        )
        dailyForecastAdapter = WeatherDailyForecastAdapter()
        weatherFragmentBinding.rvDailyForecastList.adapter = dailyForecastAdapter
    }

    private fun onSetHourlyForecastAdapter() {
        weatherFragmentBinding.rvHourlyForecastList.addItemDecoration(
            DividerItemDecoration(
                context!!,
                RecyclerView.HORIZONTAL
            )
        )
        hourlyForecastAdapter = WeatherHourlyForecastAdapter()
        weatherFragmentBinding.rvHourlyForecastList.adapter = hourlyForecastAdapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToData()
        viewModel.getLocationData()
        weatherFragmentBinding.svWeatherFragmentScroll.viewTreeObserver.addOnScrollChangedListener(
            ScrollPositionObserver()
        )
    }

    private fun subscribeToData() {
        viewModel.viewState.subscribe(this, ::handleViewState)
        viewModel.viewData.subscribe(this, ::handleViewData)
        viewModel.viewDataInfo.subscribe(this, ::handleViewDataInfo)
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
        when (viewData) {
            is Loading -> showLoading(weatherFragmentBinding.weatherLoadingProgress)
            is Success -> showWeatherDataInfo(viewData.data)
            is Error -> handleError(viewData.error.localizedMessage!!)
            is NoInternetState -> showNoInternetError()
        }
    }

    private fun handleViewDataInfo(viewDataInfo: ViewState<Weather5DaysForecast>) {
        when (viewDataInfo) {
            is Loading -> showLoading(weatherFragmentBinding.weatherLoadingProgress)
            is Success -> showWeatherDailyDataInfo(viewDataInfo.data)
            is Error -> handleError(viewDataInfo.error.localizedMessage!!)
            is NoInternetState -> showNoInternetError()
        }
    }

    private fun showWeatherDailyDataInfo(weather5DaysForecast: Weather5DaysForecast) {
        hideLoading(weatherFragmentBinding.weatherLoadingProgress)
        val dataList = weather5DaysForecast.dailyForecasts
        if (dataList.isNotEmpty()) {
            dailyForecastAdapter.updateDailyForecast(dataList)
            dailyForecastAdapter.onItemClick = { onOpenUrl(it) }

            val dayForecast = dataList[0]
            weatherDetailedInfoHashMap[sunRise] = dayForecast.sunRise
            weatherDetailedInfoHashMap[sunSet] = dayForecast.sunSet

            weatherDetailedInfoAdapter.updateData(weatherDetailedInfoHashMap)

        }
    }

    private fun showWeatherDataInfo(dataList: List<Weather12HourlyForecast>) {
        hideLoading(weatherFragmentBinding.weatherLoadingProgress)
        if (dataList.isNotEmpty()) {
            val weatherHourItem = dataList[0]
            weatherFragmentBinding.tvWeatherFragmentTemperature.text = weatherHourItem.temperature
            weatherFragmentBinding.tvWeatherFragmentPhrase.text = weatherHourItem.iconPhrase
            weatherFragmentBinding.tvWeatherFragmentTopPhrase.text = weatherHourItem.iconPhrase
            Picasso.get().load(weatherHourItem.weatherIcon).error(R.drawable.ic_broken_image_white)
                .into(weatherFragmentBinding.ivWeatherFragmentIcon)

            weatherDetailedInfoHashMap[humidity] = weatherHourItem.relativeHumidity
            weatherDetailedInfoHashMap[realFeelTemperature] = weatherHourItem.realFeelTemperature
            weatherDetailedInfoHashMap[uvIndex] = weatherHourItem.UVIndexText

            hourlyForecastAdapter.updateHourlyForecast(dataList)
            hourlyForecastAdapter.onItemClick = { onOpenUrl(it) }


        }
    }

    private fun onOpenUrl(it: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(it)
        startActivity(intent)
    }

    private fun handleError(error: String) {
        hideLoading(weatherFragmentBinding.weatherLoadingProgress)
        showError(error, weatherFragmentBinding.accuWeatherContainer)
    }

    private fun showNoInternetError() {
        hideLoading(weatherFragmentBinding.weatherLoadingProgress)
        snackbar(
            getString(R.string.no_internet_error_message),
            weatherFragmentBinding.accuWeatherContainer
        )
    }

    private fun getLocationKey(locationInfo: LocationInfo) {

        val cityName = locationInfo.localizedName
        weatherFragmentBinding.tvWeatherFragmentCity.text = cityName
        weatherFragmentBinding.tvWeatherFragmentTopCity.text = cityName

        val locationKey = locationInfo.key
        viewModel.getWeatherHourlyInfo(locationKey)
        viewModel.getWeatherDailyInfo(locationKey)
    }


    private inner class ScrollPositionObserver : ViewTreeObserver.OnScrollChangedListener {

        private val mTopLayoutHeight: Int =
            resources.getDimensionPixelSize(R.dimen.top_layout_height)

        override fun onScrollChanged() {
            val scrollY = weatherFragmentBinding.svWeatherFragmentScroll.scrollY.coerceAtLeast(0)
                .coerceAtMost(mTopLayoutHeight)

            weatherFragmentBinding.layoutWeatherFragmentTopScroll.translationY =
                (scrollY / 2).toFloat()

            val alpha = 10 * scrollY / mTopLayoutHeight.toFloat()

            weatherFragmentBinding.layoutWeatherFragmentTop.alpha = alpha
            weatherFragmentBinding.layoutWeatherFragmentTop.visibility = View.VISIBLE

        }
    }


}
