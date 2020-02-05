package kz.weather.data.networking

import kz.weather.data.di.API_KEY
import kz.weather.data.networking.model.Weather5DayForecastResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Response

interface Weather5DaysForecastApi {

    @GET("forecasts/v1/daily/5day/{key}")
    suspend fun getWeather5DaysForecast(@Path("key") key: String, @Query("apikey") apikey: String = API_KEY, @Query("details") details: String = "true", @Query("metric") metric: String = "true"): Response<Weather5DayForecastResponse>

}