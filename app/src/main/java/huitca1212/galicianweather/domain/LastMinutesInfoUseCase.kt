package huitca1212.galicianweather.domain

import huitca1212.galicianweather.data.datasource.LastMinutesInfoNetworkDataSource
import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.data.repository.LastMinutesInfoRepository


class LastMinutesInfoUseCase(
    dataSource: LastMinutesInfoNetworkDataSource,
    private val repository: LastMinutesInfoRepository = LastMinutesInfoRepository(dataSource)
    ) : UseCase<String, DataLastMinutesWrapper> {

    override suspend fun run(policy: DataPolicy, params: String): Result<DataLastMinutesWrapper> =
        repository.getLastMinutesInfo(DataPolicy.Network, params)
}