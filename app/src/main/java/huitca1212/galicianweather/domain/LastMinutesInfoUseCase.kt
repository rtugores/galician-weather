package huitca1212.galicianweather.domain

import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.data.repository.LastMinutesInfoRepository


class LastMinutesInfoUseCase(
    private val repository: LastMinutesInfoRepository
) : UseCase<GetStationsUseCaseParams, DataLastMinutesWrapper>() {

    override suspend fun run(listener: Callback<DataLastMinutesWrapper>) {
        val result = repository.getLastMinutesInfo(useCaseParams.idEst)
        listener(result)
    }

}