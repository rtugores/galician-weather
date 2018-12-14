package huitca1212.galicianweather.domain

import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.data.repository.DailyInfoRepository


class DailyInfoUseCase(
    private val repository: DailyInfoRepository
) : UseCase<GetStationsUseCaseParams, DataDailyWrapper>() {

    override suspend fun run(listener: Callback<DataDailyWrapper>) {
        val result = repository.getDailyInfo(useCaseParams.idEst)
        listener(result)
    }
}