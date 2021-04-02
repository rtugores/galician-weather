package com.galicianweather.view.util

import android.widget.ImageView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun ImageView.initRadarImage() {
    val now = Date().time - 12 * 60 * 1000 // Minus 12 minutes
    val zone = TimeZone.getTimeZone("GMT")

    val year = customDateFormatter("yyyy", now, zone)
    val month = customDateFormatter("MM", now, zone)
    val day = customDateFormatter("dd", now, zone)
    val hour = customDateFormatter("HH", now, zone)
    val minutes = SimpleDateFormat("mm", Locale.getDefault()).run {
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

private fun customDateFormatter(pattern: String, now: Long, zone: TimeZone): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).run {
        timeZone = zone
        format(now)
    }
}
