package com.galicianweather.view

import com.galicianweather.data.datasource.model.DataDailyWrapper
import com.galicianweather.data.datasource.model.DataLastMinutesWrapper
import com.galicianweather.domain.*
import com.galicianweather.view.base.BasePresenter
import com.galicianweather.view.base.BaseViewTranslator
import com.galicianweather.view.model.StationViewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class StationDetailsPresenter(
    private val view: StationViewTranslator,
    private val invoker: UseCaseInvoker,
    private val getLastMinutesInfoUseCase: GetLastMinutesInfoUseCase,
    private val getDailyInfoNetworkUseCase: GetDailyInfoUseCase
) : BasePresenter() {

    private val station: StationViewModel by lazy {
        view.getStationArg()
    }
    private var content: Any? = null

    override fun onPostCreate() {
        super.onPostCreate()
        view.initScreenInfo("${station.city} - ${station.place} ", station.imageUrl)
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
        view.finish()
    }

    fun onRetryButtonClick() {
        retrieveStationData()
    }

    private fun retrieveStationData() {
        if (view.isNetworkAvailable()) {
            view.showLoaderScreen()

            val stationParams = GetStationsUseCaseParams(station.code)
            invoker.executeParallel(
                getLastMinutesInfoUseCase withParams stationParams,
                getDailyInfoNetworkUseCase withParams stationParams
            ) { result ->
                val data = (result as Multiple).data as List<*>

                if (data.any { it is Error }) {
                    view.showUnknownError()
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
                view.showContent()
            }
        } else {
            view.showNoInternetError()
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
    fun showUnknownError()
    fun showNoInternetError()
    fun updateRadarImage()
    fun showContent()
    fun finish()
}
