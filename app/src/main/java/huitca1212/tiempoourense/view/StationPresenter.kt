package huitca1212.tiempoourense.view

import huitca1212.tiempoourense.interactor.usecase.DailyInfoUseCase
import huitca1212.tiempoourense.interactor.usecase.LastMinutesInfoUseCase
import huitca1212.tiempoourense.interactor.Success
import huitca1212.tiempoourense.model.DataDailyWrapper
import huitca1212.tiempoourense.model.DataLastMinutesWrapper
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class StationPresenter(val view: StationViewTranslator) {

    var stationId: String = ""
    private var jobs = mutableListOf<Job>()

    fun onResume() {
        retrieveStationData()
    }

    fun onPause() {
        jobs.forEach { it.cancel() }
    }

    private fun retrieveStationData() {
        view.showLoaderScreen()
        val job = launch(UI) {
            val lastMinutesInfo = LastMinutesInfoUseCase().execute(stationId).await()
            val dailyInfo = DailyInfoUseCase().execute(stationId).await()

            if (lastMinutesInfo is Success && dailyInfo is Success) {
                processLastMinutesInfo(lastMinutesInfo.response)
                processDailyInfo(dailyInfo.response)
                view.showDataScreen()
            } else {
                view.showErrorScreen()
            }
        }
        jobs.add(job)
    }

    private fun processLastMinutesInfo(lastMinutesInfo: DataLastMinutesWrapper) {
        lastMinutesInfo.getLastMinutes()?.let {
            view.updateTemperature(it.temperatureValue!!, it.temperatureUnits!!)
            if (it.rainValue!! > 0) {
                view.updateCurrentRain(it.rainValue!!, it.rainUnits!!)
            } else {
                view.updateCurrentRainNoRain()
            }
        }
    }

    private fun processDailyInfo(dailyInfo: DataDailyWrapper) {
        dailyInfo.getDataDaily()?.let {
            if (it.rainValue!! > 0) {
                view.updateDailyRain(it.rainValue!!, it.rainUnits!!)
            } else {
                view.updateDailyRainNoRain()
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