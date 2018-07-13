package huitca1212.galicianweather.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import huitca1212.galicianweather.R
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
                StationDetailsActivity.STATION_ID_OURENSE_ESTACION,
                StationDetailsActivity.STATION_ID_OURENSE_OURENSE
            )
        }

        stationsRecyclerView.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this@HomeActivity, LinearLayoutManager.VERTICAL))
            adapter = stationAdapter
        }
    }

    override fun openStationDetailsScreen(stationName: String) {
        StationDetailsActivity.startActivity(this, StationDetailsActivity.STATION_ID_OURENSE_ESTACION)
    }
}