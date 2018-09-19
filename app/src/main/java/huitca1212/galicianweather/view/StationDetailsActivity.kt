package huitca1212.galicianweather.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import huitca1212.galicianweather.R
import huitca1212.galicianweather.injection.injectActivity
import huitca1212.galicianweather.view.base.BaseActivity
import huitca1212.galicianweather.view.util.*
import kotlinx.android.synthetic.main.activity_station_datails.*

class StationDetailsActivity : BaseActivity<StationDetailsPresenter>(), StationViewTranslator {

    companion object {
        const val ARG_STATION = "arg_station"

        fun startActivity(context: Context, station: Station) {
            context.startActivity(
                Intent(context, StationDetailsActivity::class.java)
                    .putExtra(ARG_STATION, station)
            )
        }
    }

    override val presenter: StationDetailsPresenter by injectActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station_datails)

        setSupportActionBar(stationDetailsToolbar)
        supportActionBar!!.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back_arrow_white)
        }

        presenter.station = intent.extras.getSerializable(StationDetailsActivity.ARG_STATION) as Station
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

    override fun showErrorDialog() {
        infoGroup.invisible()
        progressBar.gone()
        showRetryDialog(R.string.request_failure_error) {
            presenter.onRetryButtonClick()
        }
    }

    override fun showNoInternetDialog() {
        infoGroup.invisible()
        progressBar.gone()
        showRetryDialog(R.string.request_no_internet_error) {
            presenter.onRetryButtonClick()
        }
    }

    private fun showRetryDialog(@StringRes messageId: Int, onRetry: () -> Unit) {
        AlertDialog.Builder(this)
            .setMessage(getString(messageId))
            .setPositiveButton(getString(R.string.dialog_retry)) { dialog, _ ->
                dialog.dismiss()
                onRetry()
            }.setNegativeButton(getString(R.string.dialog_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun showDataScreen() {
        infoGroup.visible()
        progressBar.gone()
    }

    override fun initScreenInfo(name: String, imageUrl: String) {
        stationDetailsToolbar.title = name
        stationDetailsImage.setImageUrl("$imageUrl${System.currentTimeMillis()}")
    }

    override fun updateTemperature(value: String, units: String) {
        infoTemperature.text = getString(R.string.temperature_last_minutes).format(value, units)
    }

    override fun updateHumidity(value: String, units: String) {
        infoHumidity.text = getString(R.string.humidity_last_minutes).format(value, units)
    }

    override fun updateCurrentRain(value: String, units: String) {
        infoRain.text = getString(R.string.rain_last_minutes).format(value, units)
    }

    override fun updateDailyRain(value: String, units: String) {
        infoRainDaily.text = getString(R.string.rain_daily).format(value, units)
    }

    override fun updateRadarImage() {
        radarImage.initRadarImage()
    }
}