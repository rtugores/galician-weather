package huitca1212.galicianweather.view

import android.os.Bundle
import android.support.annotation.DrawableRes
import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.network.StationApi
import huitca1212.galicianweather.usecase.DailyInfoUseCase
import huitca1212.galicianweather.usecase.LastMinutesInfoUseCase
import huitca1212.galicianweather.usecase.Success
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class StationPresenter(private val view: StationViewTranslator, private val stationApi: StationApi) {

    lateinit var station: Station

    fun onCreate(extras: Bundle) {
        station = extras.getSerializable(StationDetailsActivity.ARG_STATION) as Station
        view.initScreenInfo(station.name, station.imageResId)
    }

    fun onResume() {
        retrieveStationData()
    }

    private fun retrieveStationData() {
        view.showLoaderScreen()
        launch(UI) {
            val lastMinutesInfoJob = LastMinutesInfoUseCase(stationApi).execute(station.code)
            val dailyInfoJob = DailyInfoUseCase(stationApi).execute(station.code)

            val lastMinutesInfo = lastMinutesInfoJob.await()
            val dailyInfo = dailyInfoJob.await()

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
}

interface StationViewTranslator {
    fun initScreenInfo(name: String, @DrawableRes image: Int)
    fun updateTemperature(value: Float, units: String)
    fun updateCurrentRain(value: Float, units: String)
    fun updateCurrentRainNoRain()
    fun updateDailyRain(value: Float, units: String)
    fun updateDailyRainNoRain()
    fun showLoaderScreen()
    fun showErrorScreen()
    fun showDataScreen()
}