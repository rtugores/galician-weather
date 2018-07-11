package huitca1212.tiempoourense.interactor

import huitca1212.tiempoourense.network.NetworkApi
import kotlinx.coroutines.experimental.async
import java.io.IOException


class GetDailyUseCase {

    companion object {
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    fun execute(stationId: String) = async {
        try {
            val response = NetworkApi.stationApi.getDailyDataStation(stationId).execute()
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
}