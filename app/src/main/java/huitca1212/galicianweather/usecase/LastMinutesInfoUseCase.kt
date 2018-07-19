package huitca1212.galicianweather.usecase

import huitca1212.galicianweather.data.datasource.LastMinutesInfoNetworkDataSource
import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.data.repository.LastMinutesInfoRepository
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlin.coroutines.experimental.CoroutineContext


class LastMinutesInfoUseCase(
    dataSource: LastMinutesInfoNetworkDataSource,
    private val repository: LastMinutesInfoRepository = LastMinutesInfoRepository(dataSource),
    coroutineContext: CoroutineContext = DefaultDispatcher
) : BaseUseCase<String, DataLastMinutesWrapper>(coroutineContext) {

    override fun repositoryCall(params: String) = repository.getLastMinutesInfo(DataPolicy.NETWORK, params)
}