package huitca1212.galicianweather.model

import com.google.gson.annotations.SerializedName
import huitca1212.galicianweather.data.datasource.DailyInfoNetworkDataSource

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
            if (it.parameterCode == DailyInfoNetworkDataSource.RAIN_PARAM) {
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