package huitca1212.tiempoourense.business

import huitca1212.tiempoourense.model.DataDailyWrapper
import huitca1212.tiempoourense.service.ApiUtils
import kotlinx.coroutines.experimental.launch


class GetDailyUseCase : IResolver<DataDailyWrapper> {

    companion object {
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    fun execute(stationId: String, callback: (Result<DataDailyWrapper>) -> Unit) {
        launch {
            try {
                val stationDataCall = ApiUtils.dataService.getDailyDataStation(stationId)
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