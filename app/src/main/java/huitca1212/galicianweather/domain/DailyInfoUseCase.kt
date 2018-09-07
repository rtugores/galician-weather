package huitca1212.galicianweather.domain

import huitca1212.galicianweather.data.datasource.DailyInfoNetworkDataSource
import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.data.repository.DailyInfoRepository
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlin.coroutines.experimental.CoroutineContext


class DailyInfoUseCase(
    dataSource: DailyInfoNetworkDataSource,
    private val repository: DailyInfoRepository = DailyInfoRepository(dataSource),
    coroutineContext: CoroutineContext = DefaultDispatcher
) : BaseUseCase<String, DataDailyWrapper>(coroutineContext) {

    override fun repositoryCall(params: String) =
        repository.getDailyInfo(DataPolicy.NETWORK, params)
}