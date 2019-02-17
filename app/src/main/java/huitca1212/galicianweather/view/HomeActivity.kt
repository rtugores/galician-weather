package huitca1212.galicianweather.view

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import huitca1212.galicianweather.R
import huitca1212.galicianweather.injection.injectActivity
import huitca1212.galicianweather.view.base.BaseActivity
import huitca1212.galicianweather.view.model.StationViewModel
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : BaseActivity<HomePresenter>(), HomeViewTranslator {

    override val presenter: HomePresenter by injectActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(homeToolbar)

        val stations = listOf(
            StationViewModel("14000", "Coruña - Dique", "http://85.91.64.26/coruna/readImage.asp?dummy="),
            StationViewModel("10157", "Coruña - Torre de Hércules", "http://85.91.64.26/coruna/readImage.asp?dummy="),
            StationViewModel("10050", "Ferrol - CIS", "http://85.91.64.19/ferrol/readImage.asp?dummy="),
            StationViewModel("10053", "Lugo - Campus", "http://85.91.64.19/lugo/readImage.asp?dummy="),
            StationViewModel("10148", "Ourense - Campus", "http://85.91.64.26/ourense/readImage.asp?dummy="),
            StationViewModel("10155", "Ourense - Estación de bus", "http://85.91.64.26/ourense/readImage.asp?dummy="),
            StationViewModel("10156", "Pontevedra - Campolongo", "http://85.91.64.19/pontevedra/readImage.asp?dummy="),
            StationViewModel("10124", "Santiago - Campus Sur", "http://85.91.64.26/obradoiro/readImage.asp?dummy="),
            StationViewModel("50500", "Santiago - San Lázaro", "http://85.91.64.26/obradoiro/readImage.asp?dummy="),
            StationViewModel("10142", "Vigo - Plaza de España", "http://85.91.64.19/vigo/readImage.asp?dummy="),
            StationViewModel("10161", "Vigo - CUVI", "http://85.91.64.19/vigo/readImage.asp?dummy="),
            StationViewModel("14001", "Vigo - Puerto", "http://85.91.64.19/vigo/readImage.asp?dummy=")
        )

        stationsRecyclerView.run {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this@HomeActivity, LinearLayoutManager.VERTICAL))
            adapter = StationAdapter(context, stations, presenter::onStationClick)
        }
    }

    override fun openStationDetailsScreen(station: StationViewModel) {
        StationDetailsActivity.startActivity(this, station)
    }
}