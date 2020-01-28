package kz.weather.data.networking

import kz.weather.data.di.API_KEY
import kz.weather.data.networking.model.LocationInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationInfoApi {

    @GET("locations/v1/cities/geoposition/search")
    suspend fun getLocationData(@Query("apikey") apiKey: String = API_KEY, @Query("q") location: String): Response<LocationInfoResponse>

}