package huitca1212.galicianweather.usecase

import huitca1212.galicianweather.data.datasource.LastMinutesInfoNetworkDataSource
import huitca1212.galicianweather.data.repository.LastMinutesInfoRepository
import huitca1212.galicianweather.usecase.DataPolicy
import huitca1212.galicianweather.network.StationApi
import kotlinx.coroutines.experimental.async


class LastMinutesInfoUseCase(
    stationApi: StationApi,
    private val repository: LastMinutesInfoRepository = LastMinutesInfoRepository(LastMinutesInfoNetworkDataSource(stationApi))
) {

    fun execute(stationId: String) = async { repository.getLastMinutesInfo(DataPolicy.NETWORK, stationId) }
}
