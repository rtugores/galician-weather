package com.galicianweather.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.galicianweather.R
import com.galicianweather.databinding.ActivityStationDatailsBinding
import com.galicianweather.injection.injectActivity
import com.galicianweather.view.base.BaseActivity
import com.galicianweather.view.model.StationViewModel
import com.galicianweather.view.util.gone
import com.galicianweather.view.util.initRadarImage
import com.galicianweather.view.util.setImageUrl
import com.galicianweather.view.util.visible
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class StationDetailsActivity : BaseActivity<StationDetailsPresenter>(), StationViewTranslator {

    companion object {

        const val ARG_STATION = "arg_station"

        fun startActivity(context: Context, station: StationViewModel) {
            val intent = Intent(context, StationDetailsActivity::class.java).putExtra(ARG_STATION, station)
            context.startActivity(intent)
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
        binding.stationDetailsContent.radarImage.maximumScale = 3.5f
        binding.stationDetailsError.infoErrorButton.setOnClickListener{
            presenter.onRetryButtonClick()
        }
    }

    override fun getStationArg() = intent.extras?.getSerializable(ARG_STATION) as StationViewModel

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            presenter.onBackButtonClick()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showLoaderScreen() {
        with(binding) {
            stationDetailsContent.root.gone()
            stationDetailsError.root.gone()
            stationDetailsLoading.root.visible()
        }
    }

    override fun showUnknownError() {
        with(binding) {
            stationDetailsContent.root.gone()
            stationDetailsLoading.root.gone()
            stationDetailsError.root.visible()
            stationDetailsError.infoErrorText.setText(R.string.request_failure_error)
        }
    }

    override fun showNoInternetError() {
        with(binding) {
            stationDetailsContent.root.gone()
            stationDetailsLoading.root.gone()
            stationDetailsError.root.visible()
            stationDetailsError.infoErrorText.setText(R.string.request_no_internet_error)
        }
    }

    override fun showContent() {
        with(binding) {
            stationDetailsLoading.root.gone()
            stationDetailsError.root.gone()
            stationDetailsContent.root.visible()
        }
    }

    override fun initScreenInfo(name: String, imageUrl: String) {
        binding.stationDetailsToolbar.title = name
        binding.stationDetailsImage.setImageUrl(imageUrl)
    }

    override fun updateTemperature(value: String, units: String) {
        binding.stationDetailsContent.infoTemperature.text = getString(R.string.temperature_last_minutes, value, units)
    }

    override fun updateHumidity(value: String, units: String) {
        binding.stationDetailsContent.infoHumidity.text = getString(R.string.humidity_last_minutes, value, units)
    }

    override fun updateCurrentRain(value: String) {
        with(binding.stationDetailsContent) {
            try {
                val currentRain = value.replace(",", ".").toFloat()
                val currentRainStringRes = if (currentRain > 0) {
                    R.string.rain_last_minutes_raining
                } else {
                    R.string.rain_last_minutes_not_raining
                }
                infoRain.text = getString(currentRainStringRes)
                infoRain.visible()
            } catch (e: NumberFormatException) {
                infoRain.gone()
            }
        }
    }

    override fun updateDailyRain(value: String, units: String) {
        binding.stationDetailsContent.infoRainDaily.text = getString(R.string.rain_daily, value, units)
    }

    override fun updateRadarImage() {
        binding.stationDetailsContent.radarImage.initRadarImage()
    }
}
