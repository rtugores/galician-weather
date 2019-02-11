package huitca1212.galicianweather.data.repository

import huitca1212.galicianweather.data.datasource.LastMinutesInfoNetworkDataSource

class LastMinutesInfoRepository(private val dataSource: LastMinutesInfoNetworkDataSource) {

    fun getLastMinutesInfo(idEst: String) =
        dataSource.getLastMinutesInfo(idEst)
}