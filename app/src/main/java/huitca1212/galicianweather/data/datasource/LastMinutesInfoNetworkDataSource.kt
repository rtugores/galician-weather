package huitca1212.galicianweather.data.datasource

import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.domain.*
import huitca1212.galicianweather.network.StationApi
import java.io.IOException

class LastMinutesInfoNetworkDataSource(private val stationApi: StationApi) {

    companion object {
        const val TEMPERATURE_PARAM = "TA_AVG_1.5m"
        const val TEMPERATURE_PARAM_WRONG = "TA_AVG_15m"
        const val HUMIDITY_PARAM = "HR_AVG_1.5m"
        const val HUMIDITY_PARAM_WRONG = "HR_AVG_15m"
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    fun getLastMinutesInfo(useCaseParams: UseCaseParams, listener: Callback<DataLastMinutesWrapper>) = try {
        val response = stationApi.getLastMinutesDataStation(useCaseParams.remoteUseCaseParams.map).execute()
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