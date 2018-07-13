package huitca1212.galicianweather.interactor.usecase

import huitca1212.galicianweather.interactor.DataPolicy
import huitca1212.galicianweather.data.datasource.DailyInfoNetworkDataSource
import huitca1212.galicianweather.data.repository.DailyInfoRepository
import huitca1212.galicianweather.network.StationApi
import kotlinx.coroutines.experimental.async


class DailyInfoUseCase(
    stationApi: StationApi,
    private val repository: DailyInfoRepository = DailyInfoRepository(DailyInfoNetworkDataSource(stationApi))
) {

    fun execute(stationId: String) = async { repository.getDailyInfo(DataPolicy.NETWORK, stationId) }
}