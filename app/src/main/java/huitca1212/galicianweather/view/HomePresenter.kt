package huitca1212.galicianweather.view

import huitca1212.galicianweather.view.base.BasePresenter
import huitca1212.galicianweather.view.model.StationViewModel

class HomePresenter(
    private val view: HomeViewTranslator
) : BasePresenter() {

    fun onStationClick(station: StationViewModel) {
        view.openStationDetailsScreen(station)
    }
}

interface HomeViewTranslator {
    fun openStationDetailsScreen(station: StationViewModel)
}