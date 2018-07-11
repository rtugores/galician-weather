package huitca1212.tiempoourense.business

import huitca1212.tiempoourense.model.DataLastMinutesWrapper
import huitca1212.tiempoourense.service.ApiUtils


class GetLastMinutesUseCase(private val stationId: String) : DefaultUseCase<DataLastMinutesWrapper>() {

    companion object {
        const val TEMPERATURE_PARAM = "TA_AVG_1.5m"
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    override fun call() = ApiUtils.dataService.getLastMinutesDataStation(stationId)
}
