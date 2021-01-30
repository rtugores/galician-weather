package huitca1212.galicianweather.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import huitca1212.galicianweather.R
import huitca1212.galicianweather.databinding.ActivityStationDatailsBinding
import huitca1212.galicianweather.injection.injectActivity
import huitca1212.galicianweather.view.base.BaseActivity
import huitca1212.galicianweather.view.model.StationViewModel
import huitca1212.galicianweather.view.util.gone
import huitca1212.galicianweather.view.util.initRadarImage
import huitca1212.galicianweather.view.util.invisible
import huitca1212.galicianweather.view.util.setImageUrl
import huitca1212.galicianweather.view.util.visible
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class StationDetailsActivity : BaseActivity<StationDetailsPresenter>(), StationViewTranslator {

    companion object {

        const val ARG_STATION = "arg_station"

        fun startActivity(context: Context, station: StationViewModel) {
            context.startActivity(
                Intent(context, StationDetailsActivity::class.java)
                    .putExtra(ARG_STATION, station)
            )
        }
    }

    override val presenter: StationDetailsPresenter by injectActivity()
    private lateinit var binding: ActivityStationDatailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station_datails)
        binding = ActivityStationDatailsBinding.bind(findViewById(R.id.stationDetailsMainContainer))

        setSupportActionBar(binding.stationDetailsToolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back_arrow_white)
        }
        binding.radarImage.maximumScale = 3.5f
    }

    override fun getStationArg() = intent.extras?.getSerializable(ARG_STATION) as StationViewModel

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            presenter.onBackButtonClick()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showLoaderScreen() {
        binding.infoGroup.invisible()
        binding.progressBar.visible()
    }

    override fun showErrorDialog() {
        binding.infoGroup.invisible()
        binding.progressBar.gone()
        showRetryDialog(R.string.request_failure_error) {
            presenter.onRetryButtonClick()
        }
    }

    override fun showNoInternetDialog() {
        binding.infoGroup.invisible()
        binding.progressBar.gone()
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
        binding.infoGroup.visible()
        binding.progressBar.gone()
    }

    override fun initScreenInfo(name: String, imageUrl: String) {
        binding.stationDetailsToolbar.title = name
        binding.stationDetailsImage.setImageUrl(imageUrl)
    }

    override fun updateTemperature(value: String, units: String) {
        binding.infoTemperature.text = getString(R.string.temperature_last_minutes).format(value, units)
    }

    override fun updateHumidity(value: String, units: String) {
        binding.infoHumidity.text = getString(R.string.humidity_last_minutes).format(value, units)
    }

    override fun updateCurrentRain(value: String) {
        try {
            val currentRain = value.replace(",", ".").toFloat()
            binding.infoRain.text =
                getString(if (currentRain > 0) R.string.rain_last_minutes_raining else R.string.rain_last_minutes_not_raining)
            binding.infoRain.visible()
        } catch (e: NumberFormatException) {
            binding.infoRain.gone()
        }
    }

    override fun updateDailyRain(value: String, units: String) {
        binding.infoRainDaily.text = getString(R.string.rain_daily).format(value, units)
    }

    override fun updateRadarImage() {
        binding.radarImage.initRadarImage()
    }
}
