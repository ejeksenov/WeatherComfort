package kz.weather.weathercomfort.ui.weather


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
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
        var locationStr = DEFAULT_CITY_LOCATION
    }

    private lateinit var weatherFragmentBinding: WeatherFragmentBinding

    private val viewModel: WeatherViewModel by viewModel()

    private lateinit var hourlyForecastAdapter: WeatherHourlyForecastAdapter
    private lateinit var dailyForecastAdapter: WeatherDailyForecastAdapter
    private lateinit var weatherDetailedInfoAdapter: WeatherDetailedInfoAdapter

    private val weatherDetailedInfoHashMap: HashMap<String, String> = HashMap()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

        getLocationUpdates()

        weatherFragmentBinding.svWeatherFragmentScroll.viewTreeObserver.addOnScrollChangedListener(
            ScrollPositionObserver()
        )

        weatherFragmentBinding.srWeatherFragmentRefresh.setOnRefreshListener {
            viewModel.getLocationData()
        }

    }

    private fun subscribeToData() {
        viewModel.viewState.subscribe(this, ::handleViewState)
        viewModel.viewData.subscribe(this, ::handleViewData)
        viewModel.viewDataInfo.subscribe(this, ::handleViewDataInfo)
    }

    private fun handleViewState(viewState: ViewState<LocationInfo>) {
        when (viewState) {
            is Loading -> weatherFragmentBinding.srWeatherFragmentRefresh.isRefreshing = true
            is Success -> getLocationKey(viewState.data)
            is Error -> handleError(viewState.error.localizedMessage!!)
            is NoInternetState -> showNoInternetError()
        }
    }

    private fun handleViewData(viewData: ViewState<List<Weather12HourlyForecast>>) {
        when (viewData) {
            is Loading -> weatherFragmentBinding.srWeatherFragmentRefresh.isRefreshing = true
            is Success -> showWeatherDataInfo(viewData.data)
            is Error -> handleError(viewData.error.localizedMessage!!)
            is NoInternetState -> showNoInternetError()
        }
    }

    private fun handleViewDataInfo(viewDataInfo: ViewState<Weather5DaysForecast>) {
        when (viewDataInfo) {
            is Loading -> weatherFragmentBinding.srWeatherFragmentRefresh.isRefreshing = true
            is Success -> showWeatherDailyDataInfo(viewDataInfo.data)
            is Error -> handleError(viewDataInfo.error.localizedMessage!!)
            is NoInternetState -> showNoInternetError()
        }
    }

    private fun showWeatherDailyDataInfo(weather5DaysForecast: Weather5DaysForecast) {
        weatherFragmentBinding.srWeatherFragmentRefresh.isRefreshing = false
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
        weatherFragmentBinding.srWeatherFragmentRefresh.isRefreshing = false
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
        weatherFragmentBinding.srWeatherFragmentRefresh.isRefreshing = false
        showError(error, weatherFragmentBinding.accuWeatherContainer)
    }

    private fun showNoInternetError() {
        weatherFragmentBinding.srWeatherFragmentRefresh.isRefreshing = false
        snackbar(
            getString(R.string.no_internet_error_message),
            weatherFragmentBinding.accuWeatherContainer
        )
    }

    private fun getLocationKey(locationInfo: LocationInfo) {
        weatherFragmentBinding.srWeatherFragmentRefresh.isRefreshing = false
        val cityName = locationInfo.localizedName
        weatherFragmentBinding.tvWeatherFragmentCity.text = cityName
        weatherFragmentBinding.tvWeatherFragmentTopCity.text = cityName

        val locationKey = locationInfo.key
        viewModel.getWeatherHourlyInfo(locationKey)
        viewModel.getWeatherDailyInfo(locationKey)
    }

    private fun getLocationUpdates() {
        weatherFragmentBinding.srWeatherFragmentRefresh.isRefreshing = true
        locationRequest = LocationRequest()
        locationRequest.interval = 600000
        locationRequest.fastestInterval = 600000
        locationRequest.smallestDisplacement = 170f // 170 m = 0.1 mile
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    if (location != null) {
                        locationStr = "${location.latitude},${location.longitude}"
                        viewModel.getLocationData(locationStr)
                    } else {
                        snackbar(
                            getString(R.string.location_error),
                            weatherFragmentBinding.accuWeatherContainer
                        )
                    }
                    weatherFragmentBinding.srWeatherFragmentRefresh.isRefreshing = false
                }
            }
        }
    }



    //start location updates
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null /* Looper */
        )
    }

    // stop location updates
    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }


    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onResume() {
        super.onResume()
        val permissionAccessCoarseLocationApproved = ActivityCompat
            .checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED

        if (permissionAccessCoarseLocationApproved)
            startLocationUpdates()
        else
            ActivityCompat.requestPermissions(activity!!,arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 100)
    }



    private inner class ScrollPositionObserver : ViewTreeObserver.OnScrollChangedListener {

        private val mTopLayoutHeight: Int =
            resources.getDimensionPixelSize(R.dimen.top_layout_height)

        override fun onScrollChanged() {
            val scrollY = weatherFragmentBinding.svWeatherFragmentScroll.scrollY.coerceAtLeast(0)
                .coerceAtMost(mTopLayoutHeight)

            weatherFragmentBinding.layoutWeatherFragmentTopScroll.translationY =
                (scrollY / 2).toFloat()

            val alpha = 100 * scrollY / mTopLayoutHeight.toFloat()

            weatherFragmentBinding.layoutWeatherFragmentTop.alpha = alpha
            weatherFragmentBinding.layoutWeatherFragmentTop.visibility = View.VISIBLE

        }
    }


}
