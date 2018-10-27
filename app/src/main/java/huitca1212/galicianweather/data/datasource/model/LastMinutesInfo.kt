package huitca1212.galicianweather.data.datasource.model

import com.google.gson.annotations.SerializedName
import huitca1212.galicianweather.data.datasource.LastMinutesInfoNetworkDataSource


data class DataLastMinutes(
    var temperatureValue: String = "-",
    var temperatureUnits: String = "",
    var humidityValue: String = "-",
    var humidityUnits: String = "",
    var rainValue: String = "-",
    var rainUnits: String = ""
)

data class DataLastMinutesWrapper(
    @SerializedName("listUltimos10min") val list: List<DataStationLastMinutes>
) {

    fun getDataLastMinutes(): DataLastMinutes {
        val info = DataLastMinutes()
        list.firstOrNull()?.measureLastMinutes?.forEach {
            when (it.parameterCode) {
                LastMinutesInfoNetworkDataSource.TEMPERATURE_PARAM, LastMinutesInfoNetworkDataSource.TEMPERATURE_PARAM_WRONG -> {
                    if (it.value != null && it.value >= 0 && it.units != null) {
                        info.temperatureValue = String.format("%.1f", it.value)
                        info.temperatureUnits = it.units
                    }
                }
                LastMinutesInfoNetworkDataSource.HUMIDITY_PARAM, LastMinutesInfoNetworkDataSource.HUMIDITY_PARAM_WRONG -> {
                    if (it.value != null && it.value >= 0 && it.units != null) {
                        info.humidityValue = String.format("%.0f", it.value)
                        info.humidityUnits = it.units
                    }
                }
                LastMinutesInfoNetworkDataSource.RAIN_PARAM -> {
                    if (it.value != null && it.value >= 0 && it.units != null) {
                        info.rainValue = String.format("%.1f", it.value)
                        info.rainUnits = it.units
                    }
                }
            }
        }
        return info
    }
}

data class DataStationLastMinutes(
    @SerializedName("estacion") val station: String?,
    @SerializedName("listaMedidas") val measureLastMinutes: List<Measure>?,
    @SerializedName("instanteLecturaUTC") val measureTime: String?
)