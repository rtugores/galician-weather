package huitca1212.galicianweather.view

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import huitca1212.galicianweather.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(homeToolbar)

        stationsViewPager.adapter = StationsPageAdapter(supportFragmentManager)

        homeTabLayout.apply {
            addTab(newTab().setText("Ourense-Estacións"))
            addTab(newTab().setText("Ourense-Ourense"))
        }

        stationsViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(homeTabLayout))
        homeTabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(stationsViewPager))
    }
}
