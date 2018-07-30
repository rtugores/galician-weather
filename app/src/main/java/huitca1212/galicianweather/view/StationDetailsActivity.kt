package huitca1212.galicianweather.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import huitca1212.galicianweather.R
import huitca1212.galicianweather.network.StationApi
import huitca1212.galicianweather.view.util.gone
import huitca1212.galicianweather.view.util.invisible
import huitca1212.galicianweather.view.util.setImageUrl
import huitca1212.galicianweather.view.util.visible
import kotlinx.android.synthetic.main.activity_station_datails.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*
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
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back_arrow_white)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            presenter.onBackButtonClick()
        }
        return super.onOptionsItemSelected(item)
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

    override fun initScreenInfo(name: String, imageUrl: String) {
        stationDetailsToolbar.title = name
        stationDetailsImage.setImageUrl("$imageUrl${System.currentTimeMillis()}")
    }

    override fun updateTemperature(value: Float, units: String) {
        infoTemperature.text = getString(R.string.temperature_last_minutes).format(value, units)
    }

    override fun updateHumidity(value: Float, units: String) {
        infoHumidity.text = getString(R.string.humidity_last_minutes).format(value, units)
    }

    override fun updateCurrentRain(value: Float, units: String) {
        infoRain.text = if (value < 0) "-" else getString(R.string.rain_last_minutes).format(value, units)
    }

    override fun updateDailyRain(value: Float, units: String) {
        infoRainDaily.text = if (value < 0) "-" else getString(R.string.rain_daily).format(value, units)
    }

    override fun updateRadarImage() {
        val now = Date().time - 420000 // Minus 7 minutes
        val zone = "GMT"
        val year = SimpleDateFormat("yyyy", Locale.getDefault()).run {
            timeZone = TimeZone.getTimeZone(zone)
            format(now)
        }
        val month = SimpleDateFormat("MM", Locale.getDefault()).run {
            timeZone = TimeZone.getTimeZone(zone)
            format(now)
        }
        val day = SimpleDateFormat("dd", Locale.getDefault()).run {
            timeZone = TimeZone.getTimeZone(zone)
            format(now)
        }
        val hour = SimpleDateFormat("HH", Locale.getDefault()).run {
            timeZone = TimeZone.getTimeZone(zone)
            format(now)
        }
        val minutes = SimpleDateFormat("mm", Locale.getDefault()).run {
            timeZone = TimeZone.getTimeZone(zone)
            when (format(now).toInt()) {
                in 0..4 -> "55"
                in 5..14 -> "05"
                in 15..24 -> "15"
                in 25..34 -> "25"
                in 35..44 -> "35"
                in 45..54 -> "45"
                in 55..59 -> "55"
                else -> "0"
            }

        }
        radarImage.setImageUrl("http://www.meteogalicia.gal/datosred/radar/$year/$month/$day/PPI/$year$month${day}_$hour${minutes}_PPI.png")
    }
}

enum class DialogResult {
    RETRY, CANCEL
}