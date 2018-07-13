package huitca1212.galicianweather.view

class HomePresenter(
    private val view: HomeViewTranslator
) {

    fun onStationClick(stationName: String) {
        view.openStationDetailsScreen(stationName)
    }
}

interface HomeViewTranslator {
    fun openStationDetailsScreen(stationName: String)
}