package huitca1212.galicianweather.view.util

import android.widget.ImageView
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.initRadarImage() {

    val now = Date().time - 12 * 60 * 1000 // Minus 12 minutes
    val zone = TimeZone.getTimeZone("GMT")
    val locale = Locale.getDefault()

    fun customDateFormatter(pattern: String) = SimpleDateFormat(pattern, locale).run {
        timeZone = zone
        format(now)
    }

    val year = customDateFormatter("yyyy")
    val month = customDateFormatter("MM")
    val day = customDateFormatter("dd")
    val hour = customDateFormatter("HH")
    val minutes = SimpleDateFormat("mm", locale).run {
        timeZone = zone
        when (format(now).toInt()) {
            in 0..9 -> "05"
            in 10..19 -> "15"
            in 20..29 -> "25"
            in 30..39 -> "35"
            in 40..49 -> "45"
            in 50..59 -> "55"
            else -> "0"
        }
    }
    setImageUrl("http://www.meteogalicia.gal/datosred/radar/$year/$month/$day/PPI/$year$month${day}_$hour${minutes}_PPI.png")
}