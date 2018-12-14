package huitca1212.galicianweather.view

import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.domain.*
import huitca1212.galicianweather.view.base.BasePresenter

class StationDetailsPresenter(
    private val view: StationViewTranslator,
    private val invoker: UseCaseInvoker,
    private val lastMinutesInfoUseCase: LastMinutesInfoUseCase,
    private val dailyInfoNetworkUseCase: DailyInfoUseCase
) : BasePresenter() {

    private val station: Station by lazy {
        view.getStationArg()
    }
    private var content: Any? = null

    override fun onPostCreate() {
        super.onPostCreate()
        view.initScreenInfo(station.name, station.imageUrl)
    }

    override fun onStart() {
        super.onStart()
        content ?: retrieveStationData()
    }

    override fun onStop() {
        content ?: invoker.cancelAllTasks()
        super.onStop()
    }

    fun onBackButtonClick() {
        view.closeScreen()
    }

    fun onRetryButtonClick() {
        retrieveStationData()
    }

    private fun retrieveStationData() {
        view.showLoaderScreen()

        val stationParams = GetStationsUseCaseParams(station.code)
        invoker.executeParallel(
            lastMinutesInfoUseCase withParams stationParams,
            dailyInfoNetworkUseCase withParams stationParams
        ) { result ->
            val data = (result as Multiple).data as List<*>

            if (data.any { it is NoInternetError }) {
                view.showNoInternetDialog()
                return@executeParallel
            }
            if (data.any { it is UnknownError }) {
                view.showErrorDialog()
                return@executeParallel
            }

            data.forEach {
                content = (it as Success<*>).data.apply {
                    when (this) {
                        is DataLastMinutesWrapper -> processLastMinutesInfo(this)
                        is DataDailyWrapper -> processDailyInfo(this)
                    }
                }
            }
            view.updateRadarImage()
            view.showDataScreen()
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
    fun getStationArg(): Station
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
    fun closeScreen()
}
