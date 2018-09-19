package huitca1212.galicianweather.view

import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.domain.*
import huitca1212.galicianweather.view.base.BasePresenter

class StationDetailsPresenter(
    private val view: StationViewTranslator,
    private val lastMinutesInfoUseCase: LastMinutesInfoUseCase,
    private val dailyInfoNetworkDataSource: DailyInfoUseCase
) : BasePresenter() {

    lateinit var station: Station
    private val invoker = UseCaseInvoker()

    override fun onPostCreate() {
        view.initScreenInfo(station.name, station.imageUrl)
    }

    override fun onResume() {
        retrieveStationData()
    }

    override fun onPause() {
        invoker.cancelAllAsync()
    }

    fun onBackButtonClick() {
        view.finish()
    }

    fun onRetryButtonClick() {
        retrieveStationData()
    }

    private fun retrieveStationData() {
        view.showLoaderScreen()
        val useCases = listOf(
            UseCaseExecutor(lastMinutesInfoUseCase, station.code, DataPolicy.Network),
            UseCaseExecutor(dailyInfoNetworkDataSource, station.code, DataPolicy.Network)
        )
        invoker.executeParallel(useCases, {
            if (it is Success) {
                when (it.response) {
                    is DataLastMinutesWrapper -> processLastMinutesInfo(it.response)
                    is DataDailyWrapper -> processDailyInfo(it.response)
                }
            }
        }, {
            when (it) {
                is Success -> {
                    view.updateRadarImage()
                    view.showDataScreen()
                }
                is NoInternetError -> view.showNoInternetDialog()
                is Error -> view.showErrorDialog()
            }
        })
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