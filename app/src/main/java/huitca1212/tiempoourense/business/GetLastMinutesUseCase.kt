package huitca1212.tiempoourense.business

import huitca1212.tiempoourense.model.DataLastMinutesWrapper
import huitca1212.tiempoourense.service.ApiUtils
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext


class GetLastMinutesUseCase : IResolver<DataLastMinutesWrapper> {

    companion object {
        const val TEMPERATURE_PARAM = "TA_AVG_1.5m"
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    fun execute(stationId: String, callback: (Result<DataLastMinutesWrapper>) -> Unit) {
        launch {
            try {
                val stationDataCall = ApiUtils.dataService.getLastMinutesDataStation(stationId)
                val response = stationDataCall.execute()
                if (response.isSuccessful) {
                    resolveUi(callback, Success(response.body()!!))
                } else {
                    resolveUi(callback, Error())

                }
            } catch (e: Exception) {
                resolveUi(callback, Error())
            }
        }
    }
}
