package huitca1212.galicianweather.view

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import huitca1212.galicianweather.R
import huitca1212.galicianweather.databinding.ActivityHomeBinding
import huitca1212.galicianweather.injection.injectActivity
import huitca1212.galicianweather.view.base.BaseActivity
import huitca1212.galicianweather.view.model.StationViewModel
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

        val stations = listOf(
            StationViewModel("14000", "Coruña - Dique", "http://www.crtvg.es/webcam/mcoruna.jpg"),
            StationViewModel("10157", "Coruña - Torre de Hércules", "http://www.crtvg.es/webcam/coruna.jpg"),
            StationViewModel("10050", "Ferrol - CIS", "http://www.crtvg.es/webcam/ferrol.jpg"),
            StationViewModel("10053", "Lugo - Campus", "http://www.crtvg.es/webcam/lugo.jpg"),
            StationViewModel("10148", "Ourense - Campus", "http://www.crtvg.es/webcam/mourense.jpg"),
            StationViewModel("10155", "Ourense - Estación de bus", "http://www.crtvg.es/webcam/ourense.jpg"),
            StationViewModel("10156", "Pontevedra - Campolongo", "http://www.crtvg.es/webcam/pontevedra.jpg"),
            StationViewModel("10124", "Santiago - Campus Sur", "http://www.crtvg.es/webcam/praterias.jpg"),
            StationViewModel("50500", "Santiago - San Lázaro", "http://www.crtvg.es/webcam/obradoiro.jpg"),
            StationViewModel("10142", "Vigo - Plaza de España", "http://www.crtvg.es/webcam/mvigo.jpg"),
            StationViewModel("10161", "Vigo - CUVI", "http://www.crtvg.es/webcam/mvigo.jpg"),
            StationViewModel("14001", "Vigo - Puerto", "http://www.crtvg.es/webcam/mvigo.jpg")
        )

        with(binding.stationsRecyclerView) {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this@HomeActivity, LinearLayoutManager.VERTICAL))
            adapter = StationAdapter(context, stations, presenter::onStationClick)
        }
    }

    override fun openStationDetailsScreen(station: StationViewModel) {
        StationDetailsActivity.startActivity(this, station)
    }
}
