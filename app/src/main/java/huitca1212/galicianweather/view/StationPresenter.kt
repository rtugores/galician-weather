package huitca1212.galicianweather.view

import huitca1212.galicianweather.interactor.Success
import huitca1212.galicianweather.interactor.usecase.DailyInfoUseCase
import huitca1212.galicianweather.interactor.usecase.LastMinutesInfoUseCase
import huitca1212.galicianweather.model.DataDailyWrapper
import huitca1212.galicianweather.model.DataLastMinutesWrapper
import huitca1212.galicianweather.network.StationApi
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class StationPresenter(
    private val view: StationViewTranslator,
    private val stationApi: StationApi
) {

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
            val lastMinutesInfo = LastMinutesInfoUseCase(stationApi).execute(stationId).await()
            val dailyInfo = DailyInfoUseCase(stationApi).execute(stationId).await()

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