package huitca1212.galicianweather.data.datasource

import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.domain.*
import huitca1212.galicianweather.network.StationApi
import java.io.IOException

class DailyInfoNetworkDataSource(private val stationApi: StationApi) {

    companion object {
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    fun getDailyInfo(useCaseParams: UseCaseParams, listener: Callback<DataDailyWrapper>) = try {
        val response = stationApi.getDailyInfo(useCaseParams.remoteUseCaseParams.map).execute()
        if (response.isSuccessful) {
            response.body()?.let {
                listener(Success(it, DataStatus.REMOTE))
            } ?: listener(UnknownError())
        } else {
            listener(UnknownError())
        }
    } catch (e: IOException) {
        listener(NoInternetError(e))
    } catch (e: Exception) {
        listener(UnknownError(e))
    }
}