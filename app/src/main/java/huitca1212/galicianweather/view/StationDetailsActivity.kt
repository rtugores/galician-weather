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
import kotlinx.android.synthetic.main.activity_station_datails.*
import org.koin.android.ext.android.inject

class StationDetailsActivity : AppCompatActivity(), StationViewTranslator {

    companion object {
        const val ARG_STATION = "arg_station"

        fun startActivity(context: Context, station: Station) {
            context.startActivity(
                Intent(context, StationDetailsActivity::class.java)
                    .putExtra(ARG_STATION, station)
            )
        }
    }

    private val stationApi: StationApi by inject()
    private val presenter: StationPresenter = StationPresenter(this, stationApi)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station_datails)
        setSupportActionBar(stationDetailsToolbar)
        supportActionBar!!.apply {
            setDisplayShowTitleEnabled(false)
        }

        presenter.onCreate(intent.extras)
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

    override fun initScreenInfo(name: String, image: Int) {
        stationDetailsToolbar.title = name
        stationDetailsImage.setImageResource(image)
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