package huitca1212.galicianweather.usecase

import huitca1212.galicianweather.data.datasource.DailyInfoNetworkDataSource
import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.data.repository.DailyInfoRepository
import huitca1212.galicianweather.network.StationApi
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlin.coroutines.experimental.CoroutineContext


class DailyInfoUseCase(
    stationApi: StationApi,
    private val repository: DailyInfoRepository = DailyInfoRepository(DailyInfoNetworkDataSource(stationApi)),
    coroutineContext: CoroutineContext = DefaultDispatcher
) : BaseUseCase<String, DataDailyWrapper>(coroutineContext) {

    override fun repositoryCall(params: String) =
        repository.getDailyInfo(DataPolicy.NETWORK, params)
}