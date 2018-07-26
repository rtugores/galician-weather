package huitca1212.galicianweather.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import huitca1212.galicianweather.R
import huitca1212.galicianweather.network.StationApi
import huitca1212.galicianweather.view.util.gone
import huitca1212.galicianweather.view.util.invisible
import huitca1212.galicianweather.view.util.visible
import kotlinx.android.synthetic.main.activity_station_datails.*
import org.koin.android.ext.android.inject
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.suspendCoroutine

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
    private val presenter: StationDetailsPresenter = StationDetailsPresenter(this, stationApi)

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
        infoGroup.invisible()
        progressBar.visible()
    }

    override suspend fun showErrorDialog(): DialogResult {
        infoGroup.invisible()
        progressBar.gone()
        return showRetryDialog(R.string.request_failure_error)
    }

    override suspend fun showNoInternetDialog(): DialogResult {
        infoGroup.invisible()
        progressBar.gone()
        return showRetryDialog(R.string.request_no_internet_error)
    }

    private suspend fun showRetryDialog(@StringRes messageId: Int): DialogResult {
        lateinit var result: Continuation<DialogResult>
        AlertDialog.Builder(this)
            .setMessage(getString(messageId))
            .setPositiveButton(getString(R.string.dialog_retry)) { dialog, _ ->
                dialog.dismiss()
                result.resume(DialogResult.RETRY)
            }.setNegativeButton(getString(R.string.dialog_cancel)) { dialog, _ ->
                dialog.dismiss()
                result.resume(DialogResult.CANCEL)
            }
            .create()
            .show()

        return suspendCoroutine { result = it }
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
        infoTemperature.text = getString(R.string.temperature_last_minutes).format(value, units)
    }

    override fun updateCurrentRain(value: Float, units: String) {
        infoRain.text = getString(R.string.rain_last_minutes).format(value, units)
    }

    override fun updateDailyRain(value: Float, units: String) {
        infoRainDaily.text = getString(R.string.rain_daily).format(value, units)
    }
}

enum class DialogResult {
    RETRY, CANCEL
}