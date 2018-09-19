package huitca1212.galicianweather.view

import huitca1212.galicianweather.view.base.BasePresenter

class HomePresenter(
    private val view: HomeViewTranslator
) : BasePresenter() {

    fun onStationClick(station: Station) {
        view.openStationDetailsScreen(station)
    }
}

interface HomeViewTranslator {
    fun openStationDetailsScreen(station: Station)
}