package huitca1212.tiempoourense.data.datasource

import huitca1212.tiempoourense.interactor.Error
import huitca1212.tiempoourense.interactor.IOError
import huitca1212.tiempoourense.interactor.Success
import huitca1212.tiempoourense.network.NetworkApi
import java.io.IOException

class LastMinutesInfoDataSource {

    companion object {
        const val TEMPERATURE_PARAM = "TA_AVG_1.5m"
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    fun getLastMinutesInfo(stationId: String) =
        try {
            val response = NetworkApi.stationApi.getLastMinutesDataStation(stationId).execute()
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