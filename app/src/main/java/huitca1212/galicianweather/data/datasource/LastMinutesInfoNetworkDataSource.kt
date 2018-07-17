package huitca1212.galicianweather.data.datasource

import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.network.StationApi
import huitca1212.galicianweather.usecase.Error
import huitca1212.galicianweather.usecase.Result
import huitca1212.galicianweather.usecase.Success

class LastMinutesInfoNetworkDataSource(
    private val stationApi: StationApi
) {

    companion object {
        const val TEMPERATURE_PARAM = "TA_AVG_1.5m"
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    fun getLastMinutesInfo(stationId: String): Result<DataLastMinutesWrapper> {
        val response = stationApi.getLastMinutesDataStation(stationId).execute()
        if (response.isSuccessful) {
            response.body()?.let {
                return Success(it)
            } ?: return Error()
        } else {
            return Error()
        }
    }
}