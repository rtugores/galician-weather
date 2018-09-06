package huitca1212.galicianweather.view

import huitca1212.galicianweather.view.base.BasePresenter
import java.lang.ref.WeakReference

class HomePresenter(
    view: HomeViewTranslator
) : BasePresenter<HomeViewTranslator>(WeakReference(view)) {

    fun onStationClick(station: Station) {
        view?.openStationDetailsScreen(station)
    }
}

interface HomeViewTranslator {
    fun openStationDetailsScreen(station: Station)
}