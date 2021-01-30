package huitca1212.galicianweather.view.model

import java.io.Serializable

data class StationViewModel(
    val code: String,
    val city: String,
    val place: String,
    val imageUrl: String
) : Serializable
