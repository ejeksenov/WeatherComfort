package kz.weather.data.networking

import kz.weather.data.di.API_KEY
import kz.weather.data.networking.model.Weather12HourlyForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Weather12HourlyForecastApi {

    @GET("forecasts/v1/hourly/12hour/{key}")
    suspend fun getWeather12HourlyForecast(@Path("key") key: String, @Query("apikey") apikey: String = API_KEY, @Query("details") details: String = "true", @Query("metric") metric: String = "true"): Response<List<Weather12HourlyForecastResponse>>

}