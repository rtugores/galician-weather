package huitca1212.galicianweather.data.repository

import huitca1212.galicianweather.data.datasource.DailyInfoNetworkDataSource
import huitca1212.galicianweather.interactor.DataPolicy
import huitca1212.galicianweather.interactor.Error

class DailyInfoRepository(private val dataSource: DailyInfoNetworkDataSource) {

    fun getDailyInfo(policy: DataPolicy, stationId: String) =
        when (policy) {
            DataPolicy.LOCAL -> Error()
            DataPolicy.NETWORK -> dataSource.getDailyInfo(stationId)
            DataPolicy.NETWORK_AND_LOCAL -> dataSource.getDailyInfo(stationId)
        }
}