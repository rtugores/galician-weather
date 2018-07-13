package huitca1212.galicianweather.model

import android.support.annotation.DrawableRes
import java.io.Serializable


data class Station(
    val code: String,
    val name: String,
    @DrawableRes val imageResId: Int
) : Serializable