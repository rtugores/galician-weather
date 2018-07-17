package huitca1212.galicianweather.data.datasource

import huitca1212.galicianweather.usecase.Error
import huitca1212.galicianweather.usecase.IOError
import huitca1212.galicianweather.usecase.Success
import huitca1212.galicianweather.network.StationApi
import java.io.IOException

class LastMinutesInfoNetworkDataSource(
    private val stationApi: StationApi
) {

    companion object {
        const val TEMPERATURE_PARAM = "TA_AVG_1.5m"
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    fun getLastMinutesInfo(stationId: String) =
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
            IOError(e)
        } catch (e: Exception) {
            Error(e)
        }
}