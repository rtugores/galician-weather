package huitca1212.tiempoourense.model

import com.google.gson.annotations.SerializedName


data class DataLastMinutesWrapper(
    @SerializedName("listUltimos10min") val list: List<DataStationLastMinutes>
)

data class DataStationLastMinutes(
    @SerializedName("estacion") val station: String?,
    @SerializedName("listaMedidas") val measureLastMinutes: List<Measure>?,
    @SerializedName("instanteLecturaUTC") val measureTime: String?
)