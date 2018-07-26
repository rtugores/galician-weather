package huitca1212.galicianweather.data.datasource.model

import com.google.gson.annotations.SerializedName
import huitca1212.galicianweather.data.datasource.DailyInfoNetworkDataSource


data class DataDaily(
    var rainValue: Float = 0f,
    var rainUnits: String = ""
)

data class DataDailyWrapper(
    @SerializedName("listDatosDiarios") val list: List<DataStationDaily>?
) {

    fun getDataDaily(): DataDaily? {
        list?.firstOrNull()?.stations?.firstOrNull()?.measuresDaily?.forEach {
            if (it.parameterCode == DailyInfoNetworkDataSource.RAIN_PARAM) {
                return if (it.value == null || it.units == null) {
                    null
                } else {
                    DataDaily().apply {
                        rainValue = if (it.value < 0) 0f else it.value
                        rainUnits = it.units
                    }
                }
            }
        }
        return null
    }
}

data class DataStationDaily(
    @SerializedName("listaEstacions") val stations: List<StationDaily>?
)

data class StationDaily(
    @SerializedName("listaMedidas") val measuresDaily: List<Measure>?
)