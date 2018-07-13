package huitca1212.tiempoourense.data.repository

import huitca1212.tiempoourense.data.datasource.LastMinutesInfoDataSource
import huitca1212.tiempoourense.interactor.DataPolicy
import huitca1212.tiempoourense.interactor.Error

class LastMinutesInfoRepository(private val dataSource: LastMinutesInfoDataSource) {

    fun getLastMinutesInfo(policy: DataPolicy, stationId: String) =
        when (policy) {
            DataPolicy.LOCAL -> Error()
            DataPolicy.NETWORK -> dataSource.getLastMinutesInfo(stationId)
            DataPolicy.NETWORK_AND_LOCAL -> dataSource.getLastMinutesInfo(stationId)
        }
}