package kz.weather.domain.model

data class Weather5DaysForecast(val dailyForecasts: List<DayForecast>)

data class DayForecast(val date: String, val sunRise: String, val sunSet: String, val temperatureMinimum: String,
                       val temperatureMaximum: String, val dayIcon: String, val nightIcon: String, val mobileLink: String)