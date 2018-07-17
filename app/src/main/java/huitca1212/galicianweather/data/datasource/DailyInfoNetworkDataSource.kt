package huitca1212.galicianweather.data.datasource

import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.network.StationApi
import huitca1212.galicianweather.usecase.Error
import huitca1212.galicianweather.usecase.Result
import huitca1212.galicianweather.usecase.Success

class DailyInfoNetworkDataSource(private val stationApi: StationApi) {

    companion object {
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    fun getDailyInfo(stationId: String): Result<DataDailyWrapper> {
        val response = stationApi.getDailyInfo(stationId).execute()
        if (response.isSuccessful) {
            response.body()?.let {
                return Success(it)
            } ?: return Error()
        } else {
            return Error()
        }
    }
}