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
            Station("14000", "Coruña - Dique", "http://85.91.64.26/coruna/readImage.asp?dummy="),
            Station("10157", "Coruña - Torre de Hércules", "http://85.91.64.26/coruna/readImage.asp?dummy="),
            Station("10155", "Ourense - Estacións", "http://85.91.64.26/ourense/readImage.asp?dummy="),
            Station("10148", "Ourense - Ourense", "http://85.91.64.26/ourense/readImage.asp?dummy=")
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