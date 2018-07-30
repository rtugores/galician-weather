package huitca1212.galicianweather.view.util

import android.widget.ImageView
import huitca1212.galicianweather.injection.GlideApp
import java.text.SimpleDateFormat
import java.util.*


fun ImageView.setImageUrl(url: String) {
    if (url.isNotEmpty()) {
        GlideApp
            .with(context)
            .load(url)
            .into(this)
    }
}

fun ImageView.addRadarImage() {
    val now = Date().time - 420000 // Minus 7 minutes
    val zone = "GMT"
    val year = SimpleDateFormat("yyyy", Locale.getDefault()).run {
        timeZone = TimeZone.getTimeZone(zone)
        format(now)
    }
    val month = SimpleDateFormat("MM", Locale.getDefault()).run {
        timeZone = TimeZone.getTimeZone(zone)
        format(now)
    }
    val day = SimpleDateFormat("dd", Locale.getDefault()).run {
        timeZone = TimeZone.getTimeZone(zone)
        format(now)
    }
    val hour = SimpleDateFormat("HH", Locale.getDefault()).run {
        timeZone = TimeZone.getTimeZone(zone)
        format(now)
    }
    val minutes = SimpleDateFormat("mm", Locale.getDefault()).run {
        timeZone = TimeZone.getTimeZone(zone)
        when (format(now).toInt()) {
            in 0..4 -> "55"
            in 5..14 -> "05"
            in 15..24 -> "15"
            in 25..34 -> "25"
            in 35..44 -> "35"
            in 45..54 -> "45"
            in 55..59 -> "55"
            else -> "0"
        }
    }
    setImageUrl("http://www.meteogalicia.gal/datosred/radar/$year/$month/$day/PPI/$year$month${day}_$hour${minutes}_PPI.png")
}