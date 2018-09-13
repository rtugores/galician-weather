package huitca1212.galicianweather.domain

import huitca1212.galicianweather.data.datasource.DailyInfoNetworkDataSource
import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.data.repository.DailyInfoRepository


class DailyInfoUseCase(
    dataSource: DailyInfoNetworkDataSource,
    private val repository: DailyInfoRepository = DailyInfoRepository(dataSource)
) : UseCase<String, DataDailyWrapper> {

    override suspend fun run(policy: DataPolicy, params: String): Result<DataDailyWrapper> =
        repository.getDailyInfo(DataPolicy.Network, params)
}