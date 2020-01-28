package kz.weather.domain.model

data class Weather12HourlyForecast(val dateTime: String, val weatherIcon: String, val iconPhrase: String,
                                   val temperature: String, val realFeelTemperature: String, val windSpeed: String,
                                   val relativeHumidity: String, val UVIndexText: String, val mobileLink: String)