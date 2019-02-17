package huitca1212.galicianweather.view

import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.domain.*
import huitca1212.galicianweather.view.base.BasePresenter
import huitca1212.galicianweather.view.base.BaseViewTranslator
import huitca1212.galicianweather.view.model.StationViewModel

class StationDetailsPresenter(
    private val view: StationViewTranslator,
    private val invoker: UseCaseInvoker,
    private val lastMinutesInfoUseCase: LastMinutesInfoUseCase,
    private val dailyInfoNetworkUseCase: DailyInfoUseCase
) : BasePresenter() {

    private val station: StationViewModel by lazy {
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
        if (view.isNetworkAvailable()) {
            view.showLoaderScreen()

            val stationParams = GetStationsUseCaseParams(station.code)
            invoker.executeParallel(
                lastMinutesInfoUseCase withParams stationParams,
                dailyInfoNetworkUseCase withParams stationParams
            ) { result ->
                val data = (result as Multiple).data as List<*>

                if (data.any { it is Error }) {
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
        } else {
            view.showNoInternetDialog()
        }
    }

    private fun processLastMinutesInfo(lastMinutesInfo: DataLastMinutesWrapper) {
        lastMinutesInfo.getDataLastMinutes().run {
            view.updateTemperature(temperatureValue, temperatureUnits)
            view.updateHumidity(humidityValue, humidityUnits)
            view.updateCurrentRain(rainValue)
        }
    }

    private fun processDailyInfo(dailyInfo: DataDailyWrapper) {
        dailyInfo.getDataDaily().run {
            view.updateDailyRain(rainValue, rainUnits)
        }
    }
}

interface StationViewTranslator : BaseViewTranslator {
    fun getStationArg(): StationViewModel
    fun initScreenInfo(name: String, imageUrl: String)
    fun updateTemperature(value: String, units: String)
    fun updateHumidity(value: String, units: String)
    fun updateCurrentRain(value: String)
    fun updateDailyRain(value: String, units: String)
    fun showLoaderScreen()
    fun showErrorDialog()
    fun showNoInternetDialog()
    fun updateRadarImage()
    fun showDataScreen()
    fun closeScreen()
}
