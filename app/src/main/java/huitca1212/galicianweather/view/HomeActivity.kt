package huitca1212.galicianweather.view

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import huitca1212.galicianweather.R
import huitca1212.galicianweather.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity<HomePresenter>(), HomeViewTranslator {

    override val presenter = HomePresenter(this)
    override val layoutRes = R.layout.activity_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(homeToolbar)

        val stations = listOf(
            Station("14000", "Coruña - Dique", "http://85.91.64.26/coruna/readImage.asp?dummy="),
            Station("10157", "Coruña - Torre de Hércules", "http://85.91.64.26/coruna/readImage.asp?dummy="),
            Station("10053", "Lugo - Campus", "http://85.91.64.19/lugo/readImage.asp?dummy="),
            Station("10148", "Ourense - Campus", "http://85.91.64.26/ourense/readImage.asp?dummy="),
            Station("10155", "Ourense - Estación de bus", "http://85.91.64.26/ourense/readImage.asp?dummy="),
            Station("10156", "Pontevedra - Campolongo", "http://85.91.64.19/pontevedra/readImage.asp?dummy="),
            Station("10124", "Santiago - Campus Sur", "http://85.91.64.26/obradoiro/readImage.asp?dummy="),
            Station("50500", "Santiago - San Lázaro", "http://85.91.64.26/obradoiro/readImage.asp?dummy="),
            Station("10142", "Vigo - Plaza de España", "http://85.91.64.19/vigo/readImage.asp?dummy="),
            Station("10161", "Vigo - CUVI", "http://85.91.64.19/vigo/readImage.asp?dummy="),
            Station("14001", "Vigo - Puerto", "http://85.91.64.19/vigo/readImage.asp?dummy=")
        )

        stationsRecyclerView.run {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this@HomeActivity, LinearLayoutManager.VERTICAL))
            adapter = StationAdapter(context, stations, presenter::onStationClick)
        }
    }

    override fun openStationDetailsScreen(station: Station) {
        StationDetailsActivity.startActivity(this, station)
    }
}