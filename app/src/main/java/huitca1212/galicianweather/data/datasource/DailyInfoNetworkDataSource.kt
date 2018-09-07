package huitca1212.galicianweather.data.datasource

import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.network.StationApi
import huitca1212.galicianweather.domain.Error
import huitca1212.galicianweather.domain.NoInternetError
import huitca1212.galicianweather.domain.Result
import huitca1212.galicianweather.domain.Success
import java.io.IOException

class DailyInfoNetworkDataSource(private val stationApi: StationApi) {

    companion object {
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    fun getDailyInfo(stationId: String) : Result<DataDailyWrapper> =
        try {
            val response = stationApi.getDailyInfo(stationId).execute()
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