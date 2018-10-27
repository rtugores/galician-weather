package huitca1212.galicianweather.domain

import huitca1212.galicianweather.data.datasource.DailyInfoNetworkDataSource
import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.data.repository.DailyInfoRepository


class DailyInfoUseCase(
    dataSource: DailyInfoNetworkDataSource,
    private val repository: DailyInfoRepository = DailyInfoRepository(dataSource)
) : UseCase<DataDailyWrapper>() {

    override suspend fun run(listener: Callback<DataDailyWrapper>) =
        repository.getDailyInfo(useCaseParams, listener)
}