package huitca1212.galicianweather.data.datasource

import huitca1212.galicianweather.domain.DataStatus
import huitca1212.galicianweather.domain.NoInternetError
import huitca1212.galicianweather.domain.Success
import huitca1212.galicianweather.domain.UnknownError
import huitca1212.galicianweather.network.StationApi
import java.net.UnknownHostException

class DailyInfoNetworkDataSource(private val stationApi: StationApi) {

    companion object {
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    fun getDailyInfo(idEst: String) =
        try {
            val response = stationApi.getDailyInfo(mapOf("idEst" to idEst)).execute()
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