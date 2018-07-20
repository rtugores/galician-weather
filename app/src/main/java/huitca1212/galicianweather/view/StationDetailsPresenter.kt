package huitca1212.galicianweather.view

import android.os.Bundle
import android.support.annotation.DrawableRes
import huitca1212.galicianweather.data.datasource.DailyInfoNetworkDataSource
import huitca1212.galicianweather.data.datasource.LastMinutesInfoNetworkDataSource
import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.network.StationApi
import huitca1212.galicianweather.usecase.*
import huitca1212.galicianweather.view.util.CoroutinesManager

class StationDetailsPresenter(private val view: StationViewTranslator, private val stationApi: StationApi) {

    lateinit var station: Station
    private val coroutinesManager = CoroutinesManager()

    fun onCreate(extras: Bundle) {
        station = extras.getSerializable(StationDetailsActivity.ARG_STATION) as Station
        view.initScreenInfo(station.name, station.imageResId)
    }

    fun onResume() {
        retrieveStationData()
    }

    fun onPause() {
        coroutinesManager.cancelAll()
    }

    private fun retrieveStationData() {
        view.showLoaderScreen()
        coroutinesManager.launchAsync {
            val lastMinutesInfoJob = LastMinutesInfoUseCase(LastMinutesInfoNetworkDataSource(stationApi))
                .execute(station.code)
                .also { coroutinesManager.add(it) }
            val dailyInfoJob = DailyInfoUseCase(DailyInfoNetworkDataSource(stationApi))
                .execute(station.code)
                .also { coroutinesManager.add(it) }

            val lastMinutesInfo = lastMinutesInfoJob.await()
            val dailyInfo = dailyInfoJob.await()

            processResponses(lastMinutesInfo, dailyInfo)
        }
    }

    private suspend fun processResponses(lastMinutesInfo: Result<out DataLastMinutesWrapper>, dailyInfo: Result<out DataDailyWrapper>) {
        when {
            lastMinutesInfo is Success && dailyInfo is Success -> {
                processLastMinutesInfo(lastMinutesInfo.response)
                processDailyInfo(dailyInfo.response)
                view.showDataScreen()
            }
            lastMinutesInfo is NoInternetError || dailyInfo is NoInternetError ->
                if (view.showNoInternetDialog() == DialogResult.RETRY) {
                    retrieveStationData()
                }
            else ->
                if (view.showErrorDialog() == DialogResult.RETRY) {
                    retrieveStationData()
                }
        }
    }

    private suspend fun processLastMinutesInfo(lastMinutesInfo: DataLastMinutesWrapper) {
        lastMinutesInfo.getDataLastMinutes()?.let {
            view.updateTemperature(it.temperatureValue, it.temperatureUnits)
            if (it.rainValue > 0) {
                view.updateCurrentRain(it.rainValue, it.rainUnits)
            } else {
                view.updateCurrentRainNoRain()
            }
        } ?: view.showErrorDialog()
    }

    private suspend fun processDailyInfo(dailyInfo: DataDailyWrapper) {
        dailyInfo.getDataDaily()?.let {
            if (it.rainValue > 0) {
                view.updateDailyRain(it.rainValue, it.rainUnits)
            } else {
                view.updateDailyRainNoRain()
            }
        } ?: view.showErrorDialog()
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
    suspend fun showErrorDialog(): DialogResult
    suspend fun showNoInternetDialog(): DialogResult
    fun showDataScreen()
}