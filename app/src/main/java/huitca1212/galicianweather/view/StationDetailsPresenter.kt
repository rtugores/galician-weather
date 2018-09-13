package huitca1212.galicianweather.view

import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.domain.*
import huitca1212.galicianweather.view.base.BasePresenter

class StationDetailsPresenter(
    view: StationViewTranslator,
    private val lastMinutesInfoUseCase: LastMinutesInfoUseCase,
    private val dailyInfoNetworkDataSource: DailyInfoUseCase
) : BasePresenter<StationViewTranslator>(view) {

    lateinit var station: Station
    private val invoker = UseCaseInvoker()

    override fun onReady() {
        view?.initScreenInfo(station.name, station.imageUrl)
    }

    override fun onResume() {
        retrieveStationData()
    }

    override fun onPause() {
        invoker.cancelAllAsync()
    }

    fun onBackButtonClick() {
        view?.finish()
    }

    fun onRetryButtonClick() {
        retrieveStationData()
    }

    private fun retrieveStationData() {
        view?.showLoaderScreen()
        invoker.executeParallel(listOf(lastMinutesInfoUseCase, dailyInfoNetworkDataSource), station.code, DataPolicy.Network, {
            if (it is Success) {
                when (it.response) {
                    is DataLastMinutesWrapper -> processLastMinutesInfo(it.response)
                    is DataDailyWrapper -> processDailyInfo(it.response)
                }
            }
        }, {
            when (it) {
                is Success -> {
                    view?.updateRadarImage()
                    view?.showDataScreen()
                }
                is NoInternetError -> view?.showNoInternetDialog()
                is Error -> view?.showErrorDialog()
            }
        })
    }

    private fun processLastMinutesInfo(lastMinutesInfo: DataLastMinutesWrapper) {
        lastMinutesInfo.getDataLastMinutes().let {
            view?.updateTemperature(it.temperatureValue, it.temperatureUnits)
            view?.updateHumidity(it.humidityValue, it.humidityUnits)
            view?.updateCurrentRain(it.rainValue, it.rainUnits)
        }
    }

    private fun processDailyInfo(dailyInfo: DataDailyWrapper) {
        dailyInfo.getDataDaily().let {
            view?.updateDailyRain(it.rainValue, it.rainUnits)
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