package huitca1212.tiempoourense.ui

import huitca1212.tiempoourense.business.Error
import huitca1212.tiempoourense.business.GetDailyUseCase
import huitca1212.tiempoourense.business.GetLastMinutesUseCase
import huitca1212.tiempoourense.business.Success

class StationPresenter(val view: StationViewTranslator) {

    var stationId: String = ""
    private var currentTemperature: Float? = null
    private var currentTemperatureUnits: String? = null
    private var currentRain: Float? = null
    private var currentRainUnits: String? = null
    private var dailyRain: Float? = null
    private var dailyRainUnits: String? = null

    fun onResume() {
        retrieveStationData()
    }

    private fun retrieveStationData() {
        view.showLoaderScreen()

        GetLastMinutesUseCase().execute(stationId) {
            when (it) {
                is Success -> {
                    it.response.list.first().measureLastMinutes?.forEach {
                        when (it.parameterCode) {
                            GetLastMinutesUseCase.TEMPERATURE_PARAM -> {
                                currentTemperature = it.value
                                currentTemperatureUnits = it.units

                                view.updateTemperature(it.value!!, it.units!!)
                            }
                            GetLastMinutesUseCase.RAIN_PARAM -> {
                                currentRain = it.value
                                currentRainUnits = it.units

                                if (currentRain != null && currentRain!! > 0) {
                                    view.updateCurrentRain(it.value!!, it.units!!)
                                } else {
                                    view.updateCurrentRainNoRain()
                                }
                            }
                        }
                    }
                    getDailyData(stationId)
                }

                is Error -> view.showErrorScreen()
            }
        }
    }

    private fun getDailyData(stationId: String) {
        GetDailyUseCase().execute(stationId) {
            when (it) {
                is Success -> {
                    it.response.list?.get(0)?.stations?.get(0)?.measuresDaily?.forEach {
                        if (it.parameterCode == GetDailyUseCase.RAIN_PARAM) {
                            dailyRain = it.value
                            dailyRainUnits = it.units

                            if (dailyRain != null && dailyRain!! > 0) {
                                view.updateDailyRain(dailyRain!!, dailyRainUnits!!)
                            } else {
                                view.updateDailyRainNoRain()
                            }
                        }
                    }
                    view.showDataScreen()
                }
                is Error -> view.showErrorScreen()
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