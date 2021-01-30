package huitca1212.galicianweather.data.datasource.model

import com.google.gson.annotations.SerializedName
import huitca1212.galicianweather.data.datasource.LastMinutesInfoRemoteDataSource
import org.koin.core.component.KoinApiExtension

data class DataLastMinutes(
    var temperatureValue: String = "-",
    var temperatureUnits: String = "",
    var humidityValue: String = "-",
    var humidityUnits: String = "",
    var rainValue: String = "-",
    var windValue: String = "-"
)

@KoinApiExtension
data class DataLastMinutesWrapper(
    @SerializedName("listUltimos10min") val list: List<DataStationLastMinutes>?
) {

    companion object {

        private const val MIN_TEMPERATURE_ALLOWED = -50
        private const val MIN_HUMIDITY_ALLOWED = 0
        private const val MIN_RAIN_ALLOWED = 0
        private const val MIN_WIND_ALLOWED = 0
    }

    fun getDataLastMinutes(): DataLastMinutes {
        val info = DataLastMinutes()
        list?.firstOrNull()?.measureLastMinutes?.forEach {
            when (it.parameterCode) {
                LastMinutesInfoRemoteDataSource.TEMPERATURE_PARAM -> {
                    if (it.value != null && it.value >= MIN_TEMPERATURE_ALLOWED && it.units != null) {
                        info.temperatureValue = "%.1f".format(it.value)
                        info.temperatureUnits = it.units
                    }
                }

                LastMinutesInfoRemoteDataSource.HUMIDITY_PARAM -> {
                    if (it.value != null && it.value >= MIN_HUMIDITY_ALLOWED && it.units != null) {
                        info.humidityValue = "%.0f".format(it.value)
                        info.humidityUnits = it.units
                    }
                }

                LastMinutesInfoRemoteDataSource.RAIN_PARAM -> {
                    if (it.value != null && it.value >= MIN_RAIN_ALLOWED && it.units != null) {
                        info.rainValue = "%.1f".format(it.value)
                    }
                }

                LastMinutesInfoRemoteDataSource.WIND_PARAM -> {
                    if (it.value != null && it.value >= MIN_WIND_ALLOWED && it.units != null) {
                        info.windValue = "%.2f".format(it.value * 3.6)
                    }
                }
            }
        }
        return info
    }

    data class DataStationLastMinutes(
        @SerializedName("estacion") val station: String?,
        @SerializedName("listaMedidas") val measureLastMinutes: List<Measure>?,
        @SerializedName("instanteLecturaUTC") val measureTime: String?
    )
}
