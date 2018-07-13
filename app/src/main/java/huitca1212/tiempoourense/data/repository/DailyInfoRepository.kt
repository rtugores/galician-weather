package huitca1212.tiempoourense.data.repository

import huitca1212.tiempoourense.data.datasource.DailyInfoNetworkDataSource
import huitca1212.tiempoourense.interactor.DataPolicy
import huitca1212.tiempoourense.interactor.Error

class DailyInfoRepository(private val dataSource: DailyInfoNetworkDataSource) {

    fun getDailyInfo(policy: DataPolicy, stationId: String) =
        when (policy) {
            DataPolicy.LOCAL -> Error()
            DataPolicy.NETWORK -> dataSource.getDailyInfo(stationId)
            DataPolicy.NETWORK_AND_LOCAL -> dataSource.getDailyInfo(stationId)
        }
}