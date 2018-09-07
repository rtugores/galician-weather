package huitca1212.galicianweather.data.repository

import huitca1212.galicianweather.data.datasource.DailyInfoNetworkDataSource
import huitca1212.galicianweather.domain.DataPolicy
import huitca1212.galicianweather.domain.Error

class DailyInfoRepository(private val dataSource: DailyInfoNetworkDataSource) {

    fun getDailyInfo(policy: DataPolicy, stationId: String) =
        when (policy) {
            DataPolicy.LOCAL -> Error()
            DataPolicy.NETWORK -> dataSource.getDailyInfo(stationId)
        }
}