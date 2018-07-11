package huitca1212.tiempoourense.model

import com.google.gson.annotations.SerializedName
import huitca1212.tiempoourense.interactor.GetDailyUseCase

data class DataDaily(
    var rainValue: Float? = null,
    var rainUnits: String? = null
)

data class DataDailyWrapper(
    @SerializedName("listDatosDiarios") val list: List<DataStationDaily>?
) {
    fun getDataDaily(): DataDaily? {
        val info = DataDaily()
        list?.firstOrNull()?.stations?.firstOrNull()?.measuresDaily?.forEach {
            if (it.parameterCode == GetDailyUseCase.RAIN_PARAM) {
                info.rainValue = it.value
                info.rainUnits = it.units
            }
        }
        return if (info.rainValue == null || info.rainUnits == null) {
            null
        } else {
            info
        }
    }
}

data class DataStationDaily(
    @SerializedName("listaEstacions") val stations: List<StationDaily>?
)

data class StationDaily(
    @SerializedName("listaMedidas") val measuresDaily: List<Measure>?
)