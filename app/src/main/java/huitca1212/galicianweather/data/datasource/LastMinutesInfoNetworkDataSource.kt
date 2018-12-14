package huitca1212.galicianweather.data.datasource

import huitca1212.galicianweather.domain.DataStatus
import huitca1212.galicianweather.domain.NoInternetError
import huitca1212.galicianweather.domain.Success
import huitca1212.galicianweather.domain.UnknownError
import huitca1212.galicianweather.network.StationApi
import java.net.UnknownHostException

class LastMinutesInfoNetworkDataSource(private val stationApi: StationApi) {

    companion object {
        const val TEMPERATURE_PARAM = "TA_AVG_1.5m"
        const val TEMPERATURE_PARAM_WRONG = "TA_AVG_15m"
        const val HUMIDITY_PARAM = "HR_AVG_1.5m"
        const val HUMIDITY_PARAM_WRONG = "HR_AVG_15m"
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    fun getLastMinutesInfo(idEst: String) =
        try {
            val response = stationApi.getLastMinutesDataStation(mapOf("idEst" to idEst)).execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    Success(it, DataStatus.REMOTE)
                } ?: UnknownError()
            } else {
                UnknownError()
            }
        } catch (e: UnknownHostException) {
            NoInternetError(e)
        } catch (e: Exception) {
            UnknownError(e)
        }
}