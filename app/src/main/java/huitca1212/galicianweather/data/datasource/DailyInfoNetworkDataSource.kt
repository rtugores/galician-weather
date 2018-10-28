package huitca1212.galicianweather.data.datasource

import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.domain.*
import huitca1212.galicianweather.network.StationApi
import java.net.UnknownHostException

class DailyInfoNetworkDataSource(private val stationApi: StationApi) {

    companion object {
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    fun getDailyInfo(useCaseParams: UseCaseParams, listener: Callback<DataDailyWrapper>) = listener(
        try {
            val response = stationApi.getDailyInfo(useCaseParams.remoteUseCaseParams.map).execute()
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