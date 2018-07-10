package huitca1212.tiempoourense.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import huitca1212.tiempoourense.R
import huitca1212.tiempoourense.business.Error
import huitca1212.tiempoourense.business.GetDailyUseCase
import huitca1212.tiempoourense.business.GetLastMinutesUseCase
import huitca1212.tiempoourense.business.Success
import huitca1212.tiempoourense.ui.utils.gone
import huitca1212.tiempoourense.ui.utils.visible
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.fragment_weather.view.*

class StationFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

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

    private var stationId: String? = null
    private var currentTemperature: Float? = null
    private var currentRain: Float? = null
    private var dailyRain: Float? = null

    private lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_weather, container, false)
        stationId = arguments?.getString(STATION_ID_ARG, STATION_ID_OURENSE_ESTACION)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rootView.screenBackground.setImageResource(if (stationId == STATION_ID_OURENSE_ESTACION) R.drawable.estacion else R.drawable.ourense)
        rootView.swipeRefreshContainer.setOnRefreshListener(this)
    }

    override fun onResume() {
        super.onResume()
        retrieveStationData(stationId!!, true)
    }

    private fun retrieveStationData(stationId: String, showLoader: Boolean) {
        rootView.infoContainer.gone()
        rootView.progressBar.visibility = if (showLoader) View.VISIBLE else View.GONE

        GetLastMinutesUseCase().execute(stationId) {
            when (it) {
                is Success -> {
                    it.response.list.first().measureLastMinutes?.forEach {
                        when (it.parameterCode) {
                            GetLastMinutesUseCase.TEMPERATURE_PARAM -> {
                                currentTemperature = it.value
                                rootView.infoTemperature.text = ("%.1f".format(currentTemperature) + it.units)
                            }
                            GetLastMinutesUseCase.RAIN_PARAM -> {
                                currentRain = it.value
                                rootView.infoRain.text = if (currentRain != null && currentRain!! > 0) {
                                    "Lluvia: " + currentRain + it.units
                                } else {
                                    getString(R.string.no_rain)
                                }
                            }
                        }
                    }
                    getDailyData(stationId)
                }

                is Error -> {
                    rootView.infoContainer.gone()
                    rootView.progressBar.gone()
                    Toast.makeText(activity, "Algo fue mal", Toast.LENGTH_SHORT).show()
                }
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
                            rootView.infoRainDaily.text = if (dailyRain != null && dailyRain!! > 0) {
                                "Lluvia acumulada: " + dailyRain + it.units
                            } else {
                                getString(R.string.no_rain_all_day)
                            }
                        }
                    }
                    rootView.infoContainer.visible()
                    rootView.progressBar.gone()
                    swipeRefreshContainer.isRefreshing = false
                }
                is Error -> {
                    rootView.infoContainer.gone()
                    rootView.progressBar.visible()
                    Toast.makeText(activity, "Algo fue mal", Toast.LENGTH_SHORT).show()
                    rootView.swipeRefreshContainer.isRefreshing = false
                }
            }
        }
    }

    override fun onRefresh() {
        retrieveStationData(stationId!!, false)
    }
}