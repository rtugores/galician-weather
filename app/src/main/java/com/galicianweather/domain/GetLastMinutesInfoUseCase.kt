package com.galicianweather.domain

import com.galicianweather.data.datasource.model.DataLastMinutesWrapper
import com.galicianweather.data.repository.WeatherRepository
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class GetLastMinutesInfoUseCase(
    private val repository: WeatherRepository
) : UseCase<GetStationsUseCaseParams, DataLastMinutesWrapper>() {

    override suspend fun run(listener: Callback<DataLastMinutesWrapper>) {
        val result = repository.getLastMinutesInfo(useCaseParams.stationId)
        listener(result)
    }

}
