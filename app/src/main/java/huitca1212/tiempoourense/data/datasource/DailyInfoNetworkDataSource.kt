package huitca1212.tiempoourense.data.datasource

import huitca1212.tiempoourense.interactor.Error
import huitca1212.tiempoourense.interactor.IOError
import huitca1212.tiempoourense.interactor.Success
import huitca1212.tiempoourense.network.StationApi
import java.io.IOException

class DailyInfoNetworkDataSource(
    private val stationApi: StationApi
) {

    companion object {
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    fun getDailyInfo(stationId: String) =
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
            IOError(e)
        } catch (e: Exception) {
            Error(e)
        }
}