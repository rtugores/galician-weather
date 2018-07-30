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
    val now = Date().time - 12 * 60 * 1000 // Minus 12 minutes
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