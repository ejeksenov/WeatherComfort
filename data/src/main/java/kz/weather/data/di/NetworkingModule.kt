package kz.weather.data.di

import kz.weather.data.BuildConfig
import kz.weather.data.networking.LocationInfoApi
import kz.weather.data.networking.Weather12HourlyForecastApi
import kz.weather.data.networking.Weather5DaysForecastApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "http://dataservice.accuweather.com/"
const val API_KEY = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

val networkingModule = module {
    single { GsonConverterFactory.create() as Converter.Factory }
    single { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) as Interceptor }
    single {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) addInterceptor(get())
                .callTimeout(10, TimeUnit.SECONDS)
        }.build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(get())
            .build()
    }
    single {
        get<Retrofit>().create(LocationInfoApi::class.java)
    }
    single {
        get<Retrofit>().create(Weather12HourlyForecastApi::class.java)
    }
    single {
        get<Retrofit>().create(Weather5DaysForecastApi::class.java)
    }
}