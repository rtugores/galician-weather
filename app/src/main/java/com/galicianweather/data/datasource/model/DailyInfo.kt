package com.galicianweather.data.datasource.model

import com.google.gson.annotations.SerializedName
import com.galicianweather.data.datasource.DailyInfoRemoteDataSource
import org.koin.core.component.KoinApiExtension

data class DataDaily(
    var rainValue: String = "-",
    var rainUnits: String = ""
)

@KoinApiExtension
data class DataDailyWrapper(
    @SerializedName("listDatosDiarios") val list: List<DataStationDaily>?
) {

    companion object {

        private const val MIN_RAIN_ALLOWED = 0
    }

    fun getDataDaily(): DataDaily {
        val info = DataDaily()
        list?.firstOrNull()?.stations?.firstOrNull()?.measuresDaily?.forEach {
            if (it.parameterCode == DailyInfoRemoteDataSource.RAIN_PARAM) {
                if (it.value != null && it.value >= MIN_RAIN_ALLOWED && it.units != null) {
                    info.rainValue = "%.1f".format(it.value)
                    info.rainUnits = it.units
                }
            }
        }
        return info
    }

    data class DataStationDaily(
        @SerializedName("listaEstacions") val stations: List<StationDaily>?
    )

    data class StationDaily(
        @SerializedName("listaMedidas") val measuresDaily: List<Measure>?
    )
}

