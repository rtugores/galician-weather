package huitca1212.tiempoourense.model

import com.google.gson.annotations.SerializedName
import huitca1212.tiempoourense.data.datasource.LastMinutesInfoNetworkDataSource


data class DataLastMinutes(
    var temperatureValue: Float? = null,
    var temperatureUnits: String? = null,
    var rainValue: Float? = null,
    var rainUnits: String? = null
)

data class DataLastMinutesWrapper(
    @SerializedName("listUltimos10min") val list: List<DataStationLastMinutes>
) {

    fun getLastMinutes(): DataLastMinutes? {
        val info = DataLastMinutes()
        list.firstOrNull()?.measureLastMinutes?.forEach {
            when (it.parameterCode) {
                LastMinutesInfoNetworkDataSource.TEMPERATURE_PARAM -> {
                    info.temperatureValue = it.value
                    info.temperatureUnits = it.units
                }
                LastMinutesInfoNetworkDataSource.RAIN_PARAM -> {
                    info.rainValue = it.value
                    info.rainUnits = it.units
                }
            }
        }
        return if (info.temperatureValue == null || info.temperatureUnits == null || info.rainValue == null || info.rainUnits == null) {
            null
        } else {
            info
        }
    }
}

data class DataStationLastMinutes(
    @SerializedName("estacion") val station: String?,
    @SerializedName("listaMedidas") val measureLastMinutes: List<Measure>?,
    @SerializedName("instanteLecturaUTC") val measureTime: String?
)