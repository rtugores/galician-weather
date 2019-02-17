package huitca1212.galicianweather.view.model

import java.io.Serializable

data class StationViewModel(
    val code: String,
    val name: String,
    val imageUrl: String
) : Serializable