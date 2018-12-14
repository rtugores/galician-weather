package huitca1212.galicianweather.data.repository

import huitca1212.galicianweather.data.datasource.LastMinutesInfoNetworkDataSource
import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.domain.Callback

class LastMinutesInfoRepository(
    private val dataSource: LastMinutesInfoNetworkDataSource
) {

    fun getLastMinutesInfo(idEst: String) =
        dataSource.getLastMinutesInfo(idEst)
}