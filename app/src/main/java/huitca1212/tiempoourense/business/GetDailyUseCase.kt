package huitca1212.tiempoourense.business

import huitca1212.tiempoourense.model.DataDailyWrapper
import huitca1212.tiempoourense.service.ApiUtils


class GetDailyUseCase(private val stationId: String) : DefaultUseCase<DataDailyWrapper>() {

    companion object {
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    override fun call() = ApiUtils.dataService.getDailyDataStation(stationId)
}