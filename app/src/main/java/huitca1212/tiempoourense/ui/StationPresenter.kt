package huitca1212.tiempoourense.ui

import huitca1212.tiempoourense.business.GetDailyUseCase
import huitca1212.tiempoourense.business.GetLastMinutesUseCase
import huitca1212.tiempoourense.business.Success
import huitca1212.tiempoourense.model.DataDailyWrapper
import huitca1212.tiempoourense.model.DataLastMinutesWrapper
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class StationPresenter(val view: StationViewTranslator) {

    var stationId: String = ""

    fun onResume() {
        retrieveStationData()
    }

    private fun retrieveStationData() {
        view.showLoaderScreen()
        launch(UI) {
            val lastMinutesInfo = GetLastMinutesUseCase(stationId).execute().await()
            val dailyInfo = GetDailyUseCase(stationId).execute().await()

            if (lastMinutesInfo is Success && dailyInfo is Success) {
                processLastMinutesInfo(lastMinutesInfo.response)
                processDailyInfo(dailyInfo.response)
                view.showDataScreen()
            } else {
                view.showErrorScreen()
            }
        }
    }

    private fun processLastMinutesInfo(lastMinutesInfo: DataLastMinutesWrapper) {
        lastMinutesInfo.list.firstOrNull()?.measureLastMinutes?.forEach {
            when (it.parameterCode) {
                GetLastMinutesUseCase.TEMPERATURE_PARAM -> view.updateTemperature(it.value!!, it.units!!)
                GetLastMinutesUseCase.RAIN_PARAM -> {
                    if (it.value != null && it.value > 0) {
                        view.updateCurrentRain(it.value, it.units!!)
                    } else {
                        view.updateCurrentRainNoRain()
                    }
                }
            }
        }
    }

    private fun processDailyInfo(dailyInfo: DataDailyWrapper) {
        dailyInfo.list?.firstOrNull()?.stations?.firstOrNull()?.measuresDaily?.forEach {
            if (it.parameterCode == GetDailyUseCase.RAIN_PARAM) {
                if (it.value != null && it.value > 0) {
                    view.updateDailyRain(it.value, it.units!!)
                } else {
                    view.updateDailyRainNoRain()
                }
            }
        }
    }

    fun onRefresh() {
        retrieveStationData()
    }
}

interface StationViewTranslator {
    fun updateTemperature(value: Float, units: String)
    fun updateCurrentRain(value: Float, units: String)
    fun updateCurrentRainNoRain()
    fun updateDailyRain(value: Float, units: String)
    fun updateDailyRainNoRain()
    fun showLoaderScreen()
    fun showErrorScreen()
    fun showDataScreen()
}