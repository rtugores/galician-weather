package huitca1212.galicianweather.domain

import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.data.repository.WeatherRepository
import org.koin.core.component.KoinApiExtension


@KoinApiExtension
class LastMinutesInfoUseCase(
    private val repository: WeatherRepository
) : UseCase<GetStationsUseCaseParams, DataLastMinutesWrapper>() {

    override suspend fun run(listener: Callback<DataLastMinutesWrapper>) {
        val result = repository.getLastMinutesInfo(useCaseParams.stationId)
        listener(result)
    }

}
