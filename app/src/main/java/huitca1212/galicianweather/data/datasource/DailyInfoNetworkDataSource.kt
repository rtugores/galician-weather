package huitca1212.galicianweather.data.datasource

import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.network.StationApi
import huitca1212.galicianweather.usecase.Success

class DailyInfoNetworkDataSource(private val stationApi: StationApi) {

    companion object {
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    fun getDailyInfo(stationId: String): Success<DataDailyWrapper> {
        val response = stationApi.getDailyInfo(stationId).execute()
        if (response.isSuccessful) {
            response.body()?.let {
                return Success(it)
            } ?: throw Exception()
        } else {
            throw Exception()
        }
    }
}