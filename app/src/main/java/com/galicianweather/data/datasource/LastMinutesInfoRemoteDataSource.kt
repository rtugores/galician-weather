package com.galicianweather.data.datasource

import com.galicianweather.data.datasource.extensions.executeCall
import com.galicianweather.data.repository.GWNullBodyException
import com.galicianweather.network.StationApi
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class LastMinutesInfoRemoteDataSource : KoinComponent {

    companion object {
        const val TEMPERATURE_PARAM = "TA_AVG_1.5m"
        const val HUMIDITY_PARAM = "HR_AVG_1.5m"
        const val RAIN_PARAM = "PP_SUM_1.5m"
        const val WIND_PARAM = "VV_AVG_10m"
    }

    private val api by inject<StationApi>()

    fun getLastMinutesInfo(stationId: String) =
        api.getLastMinutesDataStation(mapOf("idEst" to stationId))
            .executeCall() ?: throw GWNullBodyException()
}
