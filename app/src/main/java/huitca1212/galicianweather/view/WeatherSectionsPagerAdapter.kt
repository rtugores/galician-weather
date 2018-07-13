package huitca1212.galicianweather.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class WeatherSectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment =
        StationFragment.newInstance(if (position == 0) StationFragment.STATION_ID_OURENSE_ESTACION else StationFragment.STATION_ID_OURENSE_OURENSE)

    override fun getCount(): Int = 2
}
