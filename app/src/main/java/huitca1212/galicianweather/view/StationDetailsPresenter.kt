package huitca1212.galicianweather.view

import huitca1212.galicianweather.data.datasource.DailyInfoNetworkDataSource
import huitca1212.galicianweather.data.datasource.LastMinutesInfoNetworkDataSource
import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.network.StationApi
import huitca1212.galicianweather.usecase.*
import huitca1212.galicianweather.view.base.BasePresenter
import huitca1212.galicianweather.view.util.CoroutinesManager
import java.lang.ref.WeakReference

class StationDetailsPresenter(
    view: StationViewTranslator,
    private val stationApi: StationApi
) : BasePresenter<StationViewTranslator>(WeakReference(view)) {

    lateinit var station: Station
    private val coroutinesManager = CoroutinesManager()

    override fun onReady() {
        view?.initScreenInfo(station.name, station.imageUrl)
    }

    override fun onResume() {
        retrieveStationData()
    }

    override fun onPause() {
        coroutinesManager.cancelAll()
    }

    fun onBackButtonClick() {
        view?.finish()
    }

    private fun retrieveStationData() {
        view?.showLoaderScreen()
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
                view?.updateRadarImage()
                view?.showDataScreen()
            }
            lastMinutesInfo is NoInternetError || dailyInfo is NoInternetError ->
                if (view?.showNoInternetDialog() == DialogResult.RETRY) {
                    retrieveStationData()
                }
            else ->
                if (view?.showErrorDialog() == DialogResult.RETRY) {
                    retrieveStationData()
                }
        }
    }

    private suspend fun processLastMinutesInfo(lastMinutesInfo: DataLastMinutesWrapper) {
        lastMinutesInfo.getDataLastMinutes()?.let {
            view?.updateTemperature(it.temperatureValue, it.temperatureUnits)
            view?.updateHumidity(it.humidityValue, it.humidityUnits)
            view?.updateCurrentRain(it.rainValue, it.rainUnits)
        } ?: view?.showErrorDialog()
    }

    private suspend fun processDailyInfo(dailyInfo: DataDailyWrapper) {
        dailyInfo.getDataDaily()?.let {
            view?.updateDailyRain(it.rainValue, it.rainUnits)
        } ?: view?.showErrorDialog()
    }
}

interface StationViewTranslator {
    fun initScreenInfo(name: String, imageUrl: String)
    fun updateTemperature(value: Float, units: String)
    fun updateHumidity(value: Float, units: String)
    fun updateCurrentRain(value: Float, units: String)
    fun updateDailyRain(value: Float, units: String)
    fun showLoaderScreen()
    suspend fun showErrorDialog(): DialogResult
    suspend fun showNoInternetDialog(): DialogResult
    fun updateRadarImage()
    fun showDataScreen()
    fun finish()
}