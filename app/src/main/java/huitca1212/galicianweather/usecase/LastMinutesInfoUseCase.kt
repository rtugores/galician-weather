package huitca1212.galicianweather.usecase

import huitca1212.galicianweather.data.datasource.LastMinutesInfoNetworkDataSource
import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.data.repository.LastMinutesInfoRepository
import huitca1212.galicianweather.network.StationApi
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlin.coroutines.experimental.CoroutineContext


class LastMinutesInfoUseCase(
    stationApi: StationApi,
    private val repository: LastMinutesInfoRepository = LastMinutesInfoRepository(LastMinutesInfoNetworkDataSource(stationApi)),
    coroutineContext: CoroutineContext = DefaultDispatcher
) : BaseUseCase<String, DataLastMinutesWrapper>(coroutineContext) {

    override fun repositoryCall(params: String) = repository.getLastMinutesInfo(DataPolicy.NETWORK, params)
}