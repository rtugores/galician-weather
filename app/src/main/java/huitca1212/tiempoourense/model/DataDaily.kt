package huitca1212.tiempoourense.model

import com.google.gson.annotations.SerializedName

data class DataDailyWrapper(
    @SerializedName("listDatosDiarios") val list: List<DataStationDaily>?
)

data class DataStationDaily(
    @SerializedName("listaEstacions") val stations: List<StationDaily>?
)

data class StationDaily(
    @SerializedName("listaMedidas") val measuresDaily: List<Measure>?
)