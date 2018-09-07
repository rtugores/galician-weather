package huitca1212.galicianweather.data.datasource

import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.network.StationApi
import huitca1212.galicianweather.domain.Error
import huitca1212.galicianweather.domain.NoInternetError
import huitca1212.galicianweather.domain.Result
import huitca1212.galicianweather.domain.Success
import java.io.IOException

class LastMinutesInfoNetworkDataSource(private val stationApi: StationApi) {

    companion object {
        const val TEMPERATURE_PARAM = "TA_AVG_1.5m"
        const val HUMIDITY_PARAM = "HR_AVG_1.5m"
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    fun getLastMinutesInfo(stationId: String): Result<DataLastMinutesWrapper> =
        try {
            val response = stationApi.getLastMinutesDataStation(stationId).execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    Success(it)
                } ?: Error()
            } else {
                Error()
            }
        } catch (e: IOException) {
            NoInternetError(e)
        }
}