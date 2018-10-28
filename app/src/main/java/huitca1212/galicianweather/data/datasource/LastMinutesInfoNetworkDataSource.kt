package huitca1212.galicianweather.data.datasource

import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.domain.*
import huitca1212.galicianweather.network.StationApi
import java.io.IOException
import java.net.UnknownHostException

class LastMinutesInfoNetworkDataSource(private val stationApi: StationApi) {

    companion object {
        const val TEMPERATURE_PARAM = "TA_AVG_1.5m"
        const val TEMPERATURE_PARAM_WRONG = "TA_AVG_15m"
        const val HUMIDITY_PARAM = "HR_AVG_1.5m"
        const val HUMIDITY_PARAM_WRONG = "HR_AVG_15m"
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    fun getLastMinutesInfo(useCaseParams: UseCaseParams, listener: Callback<DataLastMinutesWrapper>) = listener(
        try {
            val response = stationApi.getLastMinutesDataStation(useCaseParams.remoteUseCaseParams.map).execute()
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
    )
}