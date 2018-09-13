package huitca1212.galicianweather.data.repository

import huitca1212.galicianweather.data.datasource.LastMinutesInfoNetworkDataSource
import huitca1212.galicianweather.domain.DataPolicy
import huitca1212.galicianweather.domain.Error

class LastMinutesInfoRepository(private val dataSource: LastMinutesInfoNetworkDataSource) {

    fun getLastMinutesInfo(policy: DataPolicy, stationId: String) =
        when (policy) {
            DataPolicy.Local -> Error()
            DataPolicy.Network -> dataSource.getLastMinutesInfo(stationId)
            DataPolicy.LocalAndNetwork -> dataSource.getLastMinutesInfo(stationId)
        }
}