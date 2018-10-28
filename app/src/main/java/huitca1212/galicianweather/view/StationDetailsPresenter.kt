package huitca1212.galicianweather.view

import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.domain.*
import huitca1212.galicianweather.view.base.BasePresenter
import kotlinx.coroutines.CancellationException

class StationDetailsPresenter(
    private val view: StationViewTranslator,
    private val lastMinutesInfoUseCase: LastMinutesInfoUseCase,
    private val dailyInfoNetworkDataSource: DailyInfoUseCase
) : BasePresenter() {

    lateinit var station: Station
    private val invoker = UseCaseInvoker()

    override fun onPostCreate() {
        super.onPostCreate()
        view.initScreenInfo(station.name, station.imageUrl)
        retrieveStationData()
    }

    override fun onDestroy() {
        invoker.cancelAllTasks()
        super.onDestroy()
    }

    fun onBackButtonClick() {
        view.finish()
    }

    fun onRetryButtonClick() {
        retrieveStationData()
    }

    private fun retrieveStationData() {
        view.showLoaderScreen()

        val stationParams = GetStationsUseCaseParams(GetRemoteStationsUseCaseParams(station.code))

        invoker.executeMultiple(
            lastMinutesInfoUseCase withParams stationParams,
            dailyInfoNetworkDataSource withParams stationParams
        ) {
            when (it) {
                is Success -> {
                    when (it.data) {
                        is DataLastMinutesWrapper -> processLastMinutesInfo(it.data)
                        is DataDailyWrapper -> processDailyInfo(it.data)
                    }
                }
                is NoInternetError -> {
                    invoker.cancelAllTasks()
                    view.showNoInternetDialog()
                }
                is UnknownError -> {
                    invoker.cancelAllTasks()
                    view.showErrorDialog()
                }
                is Finish -> {
                    view.updateRadarImage()
                    view.showDataScreen()
                }
            }
        }
    }

    private fun processLastMinutesInfo(lastMinutesInfo: DataLastMinutesWrapper) {
        lastMinutesInfo.getDataLastMinutes().run {
            view.updateTemperature(temperatureValue, temperatureUnits)
            view.updateHumidity(humidityValue, humidityUnits)
            view.updateCurrentRain(rainValue, rainUnits)
        }
    }

    private fun processDailyInfo(dailyInfo: DataDailyWrapper) {
        dailyInfo.getDataDaily().run {
            view.updateDailyRain(rainValue, rainUnits)
        }
    }
}

interface StationViewTranslator {
    fun initScreenInfo(name: String, imageUrl: String)
    fun updateTemperature(value: String, units: String)
    fun updateHumidity(value: String, units: String)
    fun updateCurrentRain(value: String, units: String)
    fun updateDailyRain(value: String, units: String)
    fun showLoaderScreen()
    fun showErrorDialog()
    fun showNoInternetDialog()
    fun updateRadarImage()
    fun showDataScreen()
    fun finish()
}