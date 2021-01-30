package huitca1212.galicianweather.domain

import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.data.repository.WeatherRepository
import org.koin.core.component.KoinApiExtension


@KoinApiExtension
class DailyInfoUseCase(private val repository: WeatherRepository) : UseCase<GetStationsUseCaseParams, DataDailyWrapper>() {

    override suspend fun run(listener: Callback<DataDailyWrapper>) {
        val result = repository.getDailyInfo(useCaseParams.stationId)
        listener(result)
    }
}
