package huitca1212.galicianweather.view

import huitca1212.galicianweather.view.base.BasePresenter

class HomePresenter(
    view: HomeViewTranslator
) : BasePresenter<HomeViewTranslator>(view) {

    fun onStationClick(station: Station) {
        view?.openStationDetailsScreen(station)
    }
}

interface HomeViewTranslator {
    fun openStationDetailsScreen(station: Station)
}