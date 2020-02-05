package kz.weather.data.common.utils

object ChangeImageFormat {
    fun getImageFormat(imageIcon: Int): String {
        val imageIconStr: String = if (imageIcon < 10) "0$imageIcon" else "$imageIcon"
        return "https://developer.accuweather.com/sites/default/files/$imageIconStr-s.png"
    }
}