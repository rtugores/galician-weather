package huitca1212.tiempoourense.interactor.usecase

import huitca1212.tiempoourense.data.datasource.LastMinutesInfoNetworkDataSource
import huitca1212.tiempoourense.data.repository.LastMinutesInfoRepository
import huitca1212.tiempoourense.interactor.DataPolicy
import huitca1212.tiempoourense.network.StationApi
import kotlinx.coroutines.experimental.async


class LastMinutesInfoUseCase(
    stationApi: StationApi,
    private val repository: LastMinutesInfoRepository = LastMinutesInfoRepository(LastMinutesInfoNetworkDataSource(stationApi))
) {

    fun execute(stationId: String) = async { repository.getLastMinutesInfo(DataPolicy.NETWORK, stationId) }
}
