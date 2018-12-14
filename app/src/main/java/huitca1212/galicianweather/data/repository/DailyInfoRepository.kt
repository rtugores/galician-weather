package huitca1212.galicianweather.data.repository

import huitca1212.galicianweather.data.datasource.DailyInfoNetworkDataSource

class DailyInfoRepository(
    private val dataSource: DailyInfoNetworkDataSource
) {

    fun getDailyInfo(idEst: String) =
        dataSource.getDailyInfo(idEst)
}