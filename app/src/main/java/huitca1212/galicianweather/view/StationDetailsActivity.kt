package huitca1212.galicianweather.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import huitca1212.galicianweather.R
import huitca1212.galicianweather.network.StationApi
import huitca1212.galicianweather.view.util.gone
import huitca1212.galicianweather.view.util.showToast
import huitca1212.galicianweather.view.util.visible
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_station.*
import org.koin.android.ext.android.inject

class StationDetailsActivity : AppCompatActivity(), StationViewTranslator {

    private val stationApi: StationApi by inject()

    companion object {
        const val STATION_ID_OURENSE_ESTACION = "10155"
        const val STATION_ID_OURENSE_OURENSE = "10148"
        private const val STATION_ID_ARG = "station_id_arg"

        fun startActivity(context: Context, stationId: String) {
            context.startActivity(
                Intent(context, StationDetailsActivity::class.java)
                    .putExtra(STATION_ID_ARG, stationId)
            )
        }
    }

    private var stationId: String = ""
    private val presenter: StationPresenter = StationPresenter(this, stationApi)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station)

        setSupportActionBar(stationToolbar)

        stationId = intent.extras!!.getString(STATION_ID_ARG, STATION_ID_OURENSE_ESTACION)
        presenter.stationId = stationId

        screenBackground.setImageResource(if (stationId == STATION_ID_OURENSE_ESTACION) R.drawable.estacion else R.drawable.ourense)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        presenter.onPause()
        super.onPause()
    }

    override fun showLoaderScreen() {
        infoGroup.gone()
        progressBar.visible()
    }

    override fun showErrorScreen() {
        infoGroup.gone()
        progressBar.gone()
        showToast(R.string.request_failure_error)
    }

    override fun showDataScreen() {
        infoGroup.visible()
        progressBar.gone()
    }

    override fun updateTemperature(value: Float, units: String) {
        infoTemperature.text = ("%.1f".format(value) + units)
    }

    override fun updateCurrentRain(value: Float, units: String) {
        infoRain.text = ("Lluvia: $value$units")
    }

    override fun updateCurrentRainNoRain() {
        infoRain.text = getString(R.string.no_rain)
    }

    override fun updateDailyRain(value: Float, units: String) {
        infoRainDaily.text = ("Lluvia acumulada: $value$units")
    }

    override fun updateDailyRainNoRain() {
        infoRainDaily.text = getString(R.string.no_rain_all_day)
    }
}