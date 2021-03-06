package com.galicianweather.view

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.galicianweather.R
import com.galicianweather.databinding.ActivityHomeBinding
import com.galicianweather.injection.injectActivity
import com.galicianweather.view.base.BaseActivity
import com.galicianweather.view.model.StationViewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class HomeActivity : BaseActivity<HomePresenter>(), HomeViewTranslator {

    override val presenter: HomePresenter by injectActivity()
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.bind(findViewById(R.id.homeMainContainer))

        setSupportActionBar(binding.homeToolbar)

        with(binding.stationsRecyclerView) {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this@HomeActivity, LinearLayoutManager.VERTICAL))
            adapter = StationAdapter(presenter::onStationClick)
        }
    }

    override fun showStations(stations: List<StationViewModel>) {
        (binding.stationsRecyclerView.adapter as StationAdapter).updateStations(stations)
    }

    override fun openStationDetailsScreen(station: StationViewModel) {
        StationDetailsActivity.startActivity(this, station)
    }
}
