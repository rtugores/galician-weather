package huitca1212.galicianweather.data.repository

import huitca1212.galicianweather.data.datasource.DailyInfoNetworkDataSource
import huitca1212.galicianweather.usecase.DataPolicy

class DailyInfoRepository(private val dataSource: DailyInfoNetworkDataSource) {

    fun getDailyInfo(policy: DataPolicy, stationId: String) =
        when (policy) {
            DataPolicy.LOCAL -> throw Exception()
            DataPolicy.NETWORK -> dataSource.getDailyInfo(stationId)
            DataPolicy.NETWORK_AND_LOCAL -> dataSource.getDailyInfo(stationId)
        }
}