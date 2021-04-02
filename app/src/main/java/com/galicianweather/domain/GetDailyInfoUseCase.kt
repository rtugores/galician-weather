package com.galicianweather.domain

import com.galicianweather.data.datasource.model.DataDailyWrapper
import com.galicianweather.data.repository.WeatherRepository
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class GetDailyInfoUseCase(
    private val repository: WeatherRepository
) : UseCase<GetStationsUseCaseParams, DataDailyWrapper>() {

    override suspend fun run(listener: Callback<DataDailyWrapper>) {
        val result = repository.getDailyInfo(useCaseParams.stationId)
        listener(result)
    }
}
