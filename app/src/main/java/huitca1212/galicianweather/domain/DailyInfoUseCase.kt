package huitca1212.galicianweather.domain

import huitca1212.galicianweather.data.datasource.DailyInfoNetworkDataSource
import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.data.repository.DailyInfoRepository


class DailyInfoUseCase(
    dataSource: DailyInfoNetworkDataSource,
    private val repository: DailyInfoRepository = DailyInfoRepository(dataSource)
) : UseCase<DataDailyWrapper>() {

    override suspend fun run(listener: ((Result<DataDailyWrapper>) -> Unit)) =
        repository.getDailyInfo(useCaseParams, listener)

}