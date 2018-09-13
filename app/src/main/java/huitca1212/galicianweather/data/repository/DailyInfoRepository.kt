package huitca1212.galicianweather.data.repository

import huitca1212.galicianweather.data.datasource.DailyInfoNetworkDataSource
import huitca1212.galicianweather.domain.DataPolicy
import huitca1212.galicianweather.domain.Error

class DailyInfoRepository(private val dataSource: DailyInfoNetworkDataSource) {

    fun getDailyInfo(policy: DataPolicy, stationId: String) =
        when (policy) {
            DataPolicy.Local -> Error()
            DataPolicy.Network -> dataSource.getDailyInfo(stationId)
            DataPolicy.LocalAndNetwork -> dataSource.getDailyInfo(stationId)
        }
}