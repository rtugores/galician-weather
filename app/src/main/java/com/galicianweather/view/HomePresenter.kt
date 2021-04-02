package com.galicianweather.view

import com.galicianweather.view.base.BasePresenter
import com.galicianweather.view.model.StationViewModel

class HomePresenter(private val view: HomeViewTranslator) : BasePresenter() {

    override fun onPostCreate() {
        super.onPostCreate()
        val stations = getStations()
        view.showStations(stations)
    }

    fun onStationClick(station: StationViewModel) {
        view.openStationDetailsScreen(station)
    }

    private fun getStations(): List<StationViewModel> {
        return listOf(
            StationViewModel("14000", "Coruña", "Dique", "http://www.crtvg.es/webcam/mcoruna.jpg"),
            StationViewModel("10157", "Coruña ", "Torre de Hércules", "http://www.crtvg.es/webcam/coruna.jpg"),
            StationViewModel("10050", "Ferrol ", "CIS", "http://www.crtvg.es/webcam/ferrol.jpg"),
            StationViewModel("10053", "Lugo ", "Campus", "http://www.crtvg.es/webcam/lugo.jpg"),
            StationViewModel("10148", "Ourense ", "Campus", "http://www.crtvg.es/webcam/mourense.jpg"),
            StationViewModel("10155", "Ourense ", "Estación de bus", "http://www.crtvg.es/webcam/ourense.jpg"),
            StationViewModel("10156", "Pontevedra ", "Campolongo", "http://www.crtvg.es/webcam/pontevedra.jpg"),
            StationViewModel("10124", "Santiago ", "Campus Sur", "http://www.crtvg.es/webcam/praterias.jpg"),
            StationViewModel("50500", "Santiago ", "San Lázaro", "http://www.crtvg.es/webcam/obradoiro.jpg"),
            StationViewModel("10142", "Vigo ", "Plaza de España", "http://www.crtvg.es/webcam/mvigo.jpg"),
            StationViewModel("10161", "Vigo ", "CUVI", "http://www.crtvg.es/webcam/mvigo.jpg"),
            StationViewModel("14001", "Vigo ", "Puerto", "http://www.crtvg.es/webcam/mvigo.jpg")
        )
    }
}

interface HomeViewTranslator {

    fun openStationDetailsScreen(station: StationViewModel)
    fun showStations(stations: List<StationViewModel>)
}
