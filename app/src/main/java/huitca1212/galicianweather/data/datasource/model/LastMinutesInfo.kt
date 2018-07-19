package huitca1212.galicianweather.data.datasource.model

import com.google.gson.annotations.SerializedName
import huitca1212.galicianweather.data.datasource.LastMinutesInfoNetworkDataSource


data class DataLastMinutes(
    var temperatureValue: Float = -1f,
    var temperatureUnits: String = "",
    var rainValue: Float = -1f,
    var rainUnits: String = ""
)

data class DataLastMinutesWrapper(
    @SerializedName("listUltimos10min") val list: List<DataStationLastMinutes>
) {

    fun getDataLastMinutes(): DataLastMinutes? {
        val info = DataLastMinutes()
        list.firstOrNull()?.measureLastMinutes?.forEach {
            when (it.parameterCode) {
                LastMinutesInfoNetworkDataSource.TEMPERATURE_PARAM -> {
                    if (it.value == null || it.units == null) {
                        return null
                    } else {
                        info.temperatureValue = it.value
                        info.temperatureUnits = it.units
                    }
                }
                LastMinutesInfoNetworkDataSource.RAIN_PARAM -> {
                    if (it.value == null || it.units == null) {
                        return null
                    } else {
                        info.rainValue = it.value
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