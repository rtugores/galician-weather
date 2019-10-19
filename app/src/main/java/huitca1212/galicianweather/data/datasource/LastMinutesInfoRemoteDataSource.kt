package huitca1212.galicianweather.data.datasource

import huitca1212.galicianweather.data.datasource.extensions.executeCall
import huitca1212.galicianweather.data.repository.GWNullBodyException
import huitca1212.galicianweather.network.StationApi
import org.koin.core.KoinComponent
import org.koin.core.inject

class LastMinutesInfoRemoteDataSource : KoinComponent {

    companion object {
        const val TEMPERATURE_PARAM = "TA_AVG_1.5m"
        const val TEMPERATURE_PARAM_WRONG = "TA_AVG_15m"
        const val HUMIDITY_PARAM = "HR_AVG_1.5m"
        const val HUMIDITY_PARAM_WRONG = "HR_AVG_15m"
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    private val api by inject<StationApi>()

    fun getLastMinutesInfo(stationId: String) =
        api.getLastMinutesDataStation(mapOf("idEst" to stationId))
            .executeCall() ?: throw GWNullBodyException()
}