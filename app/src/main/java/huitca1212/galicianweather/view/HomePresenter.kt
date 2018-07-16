package huitca1212.galicianweather.view

class HomePresenter(private val view: HomeViewTranslator) {

    fun onStationClick(station: Station) {
        view.openStationDetailsScreen(station)
    }
}

interface HomeViewTranslator {
    fun openStationDetailsScreen(station: Station)
}