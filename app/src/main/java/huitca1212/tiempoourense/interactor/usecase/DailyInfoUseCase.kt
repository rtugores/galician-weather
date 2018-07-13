package huitca1212.tiempoourense.interactor.usecase

import huitca1212.tiempoourense.interactor.DataPolicy
import huitca1212.tiempoourense.data.datasource.DailyInfoNetworkDataSource
import huitca1212.tiempoourense.data.repository.DailyInfoRepository
import huitca1212.tiempoourense.network.StationApi
import kotlinx.coroutines.experimental.async


class DailyInfoUseCase(
    stationApi: StationApi,
    private val repository: DailyInfoRepository = DailyInfoRepository(DailyInfoNetworkDataSource(stationApi))
) {

    fun execute(stationId: String) = async { repository.getDailyInfo(DataPolicy.NETWORK, stationId) }
}