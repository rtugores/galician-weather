package huitca1212.tiempoourense.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import huitca1212.tiempoourense.R
import huitca1212.tiempoourense.view.util.gone
import huitca1212.tiempoourense.view.util.showToast
import huitca1212.tiempoourense.view.util.visible
import kotlinx.android.synthetic.main.fragment_weather.view.*

class StationFragment : Fragment(), StationViewTranslator, SwipeRefreshLayout.OnRefreshListener {

    companion object {

        const val STATION_ID_OURENSE_ESTACION = "10155"
        const val STATION_ID_OURENSE_OURENSE = "10148"
        private const val STATION_ID_ARG = "station_id_arg"

        fun newInstance(stationId: String): Fragment {
            return StationFragment().apply {
                arguments = Bundle().also {
                    it.putString(STATION_ID_ARG, stationId)
                }
            }
        }
    }

    private var stationId: String = ""
    private lateinit var rootView: View
    private val presenter: StationPresenter = StationPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_weather, container, false)
        stationId = arguments!!.getString(STATION_ID_ARG, STATION_ID_OURENSE_ESTACION)
        presenter.stationId = stationId

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rootView.screenBackground.setImageResource(if (stationId == STATION_ID_OURENSE_ESTACION) R.drawable.estacion else R.drawable.ourense)
        rootView.swipeRefreshContainer.setOnRefreshListener(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun showLoaderScreen() {
        rootView.infoGroup.gone()
        rootView.progressBar.visible()
    }

    override fun showErrorScreen() {
        with(rootView) {
            swipeRefreshContainer.isRefreshing = false
            infoGroup.gone()
            progressBar.gone()
        }
        activity?.run {
            showToast(R.string.request_failure_error)
        }
    }

    override fun showDataScreen() {
        with(rootView) {
            swipeRefreshContainer.isRefreshing = false
            infoGroup.visible()
            progressBar.gone()
        }
    }

    override fun updateTemperature(value: Float, units: String) {
        rootView.infoTemperature.text = ("%.1f".format(value) + units)
    }

    override fun updateCurrentRain(value: Float, units: String) {
        rootView.infoRain.text = ("Lluvia: $value$units")
    }

    override fun updateCurrentRainNoRain() {
        rootView.infoRain.text = getString(R.string.no_rain)
    }

    override fun updateDailyRain(value: Float, units: String) {
        rootView.infoRainDaily.text = ("Lluvia acumulada: $value$units")
    }

    override fun updateDailyRainNoRain() {
        rootView.infoRainDaily.text = getString(R.string.no_rain_all_day)
    }

    override fun onRefresh() {
        presenter.onRefresh()
    }
}