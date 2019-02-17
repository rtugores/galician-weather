package huitca1212.galicianweather.data.datasource.model

import com.google.gson.annotations.SerializedName
import huitca1212.galicianweather.data.datasource.DailyInfoNetworkDataSource


data class DataDaily(
    var rainValue: String = "-",
    var rainUnits: String = ""
)

data class DataDailyWrapper(
    @SerializedName("listDatosDiarios") val list: List<DataStationDaily>?
) {

    companion object {
        private const val MIN_RAIN_ALLOWED = 0
    }

    fun getDataDaily(): DataDaily {
        val info = DataDaily()
        list?.firstOrNull()?.stations?.firstOrNull()?.measuresDaily?.forEach {
            if (it.parameterCode == DailyInfoNetworkDataSource.RAIN_PARAM) {
                if (it.value != null && it.value >= MIN_RAIN_ALLOWED && it.units != null) {
                    info.rainValue = "%.1f".format(it.value)
                    info.rainUnits = it.units
                }
            }
        }
        return info
    }
}

data class DataStationDaily(
    @SerializedName("listaEstacions") val stations: List<StationDaily>?
)

data class StationDaily(
    @SerializedName("listaMedidas") val measuresDaily: List<Measure>?
)