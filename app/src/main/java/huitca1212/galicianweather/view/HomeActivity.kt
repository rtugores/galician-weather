package huitca1212.galicianweather.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import huitca1212.galicianweather.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), HomeViewTranslator {

    private val presenter: HomePresenter = HomePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(homeToolbar)

        val stations = listOf(
            Station("14000", "Coruña - Dique", R.drawable.coruna_dique),
            Station("10157", "Coruña - Torre de Hércules", R.drawable.coruna_torre_de_hercules),
            Station("10155", "Ourense - Estacións", R.drawable.ourense_estacions),
            Station("10148", "Ourense - Ourense", R.drawable.ourense_ourense)
        )

        stationsRecyclerView.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this@HomeActivity, LinearLayoutManager.VERTICAL))
            adapter = StationAdapter(context, stations, presenter::onStationClick)
        }
    }

    override fun openStationDetailsScreen(station: Station) {
        StationDetailsActivity.startActivity(this, station)
    }
}