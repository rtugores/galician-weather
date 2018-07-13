package huitca1212.galicianweather.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import huitca1212.galicianweather.R
import huitca1212.galicianweather.model.Station
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), HomeViewTranslator {

    private lateinit var stationAdapter: StationAdapter
    private val presenter: HomePresenter = HomePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(homeToolbar)

        stationAdapter = StationAdapter(this) {
            presenter.onStationClick(it)
        }.apply {
            stationNames = mutableListOf(
                Station("10155", "Ourense-Estacións", R.drawable.estacion),
                Station("10148", "Ourense - Ourense", R.drawable.ourense)
            )
        }

        stationsRecyclerView.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this@HomeActivity, LinearLayoutManager.VERTICAL))
            adapter = stationAdapter
        }
    }

    override fun openStationDetailsScreen(station: Station) {
        StationDetailsActivity.startActivity(this, station)
    }
}